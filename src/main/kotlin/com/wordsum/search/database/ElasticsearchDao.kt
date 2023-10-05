package com.wordsum.search.database

import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.searchdsls.querydsl.bool
import com.jillesvangurp.searchdsls.querydsl.match
import com.jillesvangurp.searchdsls.querydsl.matchAll
import com.wordsum.search.config.ElasticsearchConfig
import com.wordsum.search.config.WordSumConfig
import com.wordsum.search.model.Story
import com.wordsum.search.model.StoryInput
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.stereotype.Component

@Component
class ElasticsearchDao(
    val elasticsearchConfig: ElasticsearchConfig,
    val wordSumConfig: WordSumConfig,
) {

    suspend fun getIndex(indexInput: String): JsonObject {
        return elasticsearchConfig.reactiveSearchClient().getIndex(indexInput)
    }
    suspend fun deleteIndex(inputIndex: String): String {
        elasticsearchConfig.reactiveSearchClient().deleteIndex(inputIndex)
        return inputIndex
    }

    suspend fun createIndex(indexInput: String): String {
       return elasticsearchConfig.reactiveSearchClient().createIndex(indexInput) {
           settings {
               replicas = 0
               shards = 3
           }
           mappings {
               text(Story::summary)
           }
       }.index
    }

    suspend fun addStory(storyInput: StoryInput): String {
        return elasticsearchConfig.reactiveSearchClient().indexDocument(
            target = wordSumConfig.index,
            document = storyInput
        ).id
    }

    suspend fun deleteStory(id: String): String {
        return elasticsearchConfig.reactiveSearchClient().deleteDocument(
            target = wordSumConfig.index,
            id = id
        ).id
    }

    suspend fun matchId(id: String): String {
        return elasticsearchConfig.reactiveSearchClient().search(wordSumConfig.index) {
            query = bool {
                must(
                    match(Story::id, id)
                )
            }
        }.ids[0]
    }

    suspend fun matchAll(): List<String> {
        return elasticsearchConfig.reactiveSearchClient().search(wordSumConfig.index) {
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
        return elasticsearchConfig.reactiveSearchClient().search(wordSumConfig.index) {
            query = bool {
                must(
                    match(Story::genre, text)
                )
            }
        }.ids
    }

    suspend fun searchDocument(text: String): List<Story> {
        return elasticsearchConfig.reactiveSearchClient().search(wordSumConfig.index) {
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