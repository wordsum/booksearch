package com.wordsum.search.database

import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.searchdsls.querydsl.bool
import com.jillesvangurp.searchdsls.querydsl.match
import com.jillesvangurp.searchdsls.querydsl.matchAll
import com.wordsum.search.config.ElasticsearchConfig
import com.wordsum.search.model.Story
import com.wordsum.search.model.StoryInput
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class ElasticsearchDao(
    val elasticsearchConfig: ElasticsearchConfig
) {
    fun indexDocuments(documents: List<Story>) {
        documents.chunked(5).forEach {
            runBlocking {
                elasticsearchConfig.reactiveSearchClient().bulk {
                    it.forEach { esDocument ->
                        index(
                            source = DEFAULT_JSON.encodeToString(Story.serializer(), esDocument),
                            index = "story",
                            id = esDocument.id
                        )
                    }
                }
            }
        }
    }

    suspend fun addStory(storyInput: StoryInput): String {
        return elasticsearchConfig.reactiveSearchClient().indexDocument(
            target = "story",
            document = storyInput
        ).id
    }

    suspend fun deleteStory(id: String): String {
        return elasticsearchConfig.reactiveSearchClient().deleteDocument(
            target = "story",
            id = id
        ).id
    }

    suspend fun matchId(id: String): String {
        return elasticsearchConfig.reactiveSearchClient().search("story") {
            query = bool {
                must(
                    match(Story::id, id)
                )
            }
        }.ids[0]

    }

    suspend fun matchAll(): List<String> {
        return elasticsearchConfig.reactiveSearchClient().search("story") {
            query = bool {
                must(
                    matchAll()
                )
            }
        }.ids
    }

    suspend fun autocomplete(text: String, start: Int, hits: Int): List<String> {
        return elasticsearchConfig.reactiveSearchClient().search("autocomplete") {
            from = start
            resultSize = hits
            query = if(text.isBlank()) {
                matchAll()
            } else {
                match("title.autocomplete", text)
            }

        }.ids
    }


    suspend fun searchDocumentIds(text: String): List<String> {
        return elasticsearchConfig.reactiveSearchClient().search("story") {
            query = bool {
                must(
                    match(Story::genre, text)
                )
            }
        }.ids
    }

    suspend fun searchDocument(text: String): List<Story> {
        return elasticsearchConfig.reactiveSearchClient().search("story") {
            query = bool {
                must(
                    match(Story::genre, text)
                )
            }
        }.hits?.hits?.map {
            deserializeHit(it)
        }.orEmpty()
    }

    private fun deserializeHit(hit: SearchResponse.Hit) = DEFAULT_JSON.decodeFromString(Story.serializer(), hit.source.toString())

}