package com.wordsum.search

import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.searchdsls.querydsl.bool
import com.jillesvangurp.searchdsls.querydsl.match
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component

@Component
class ElasticClient {

    val client = SearchClient(KtorRestClient("localhost", 9200))

    fun indexDocuments(documents: List<StoryDAO>) {
        documents.chunked(5).forEach {
            runBlocking {
                client.bulk {
                    it.forEach { esDocument ->
                        index(
                            source = DEFAULT_JSON.encodeToString(StoryDAO.serializer(), esDocument),
                            index = "story",
                            id = esDocument.id
                        )
                    }
                }
            }
        }
    }

    suspend fun searchDocumentIds(text: String): List<String> {
        return client.search("story") {
            query = bool {
                must(
                    match(StoryDAO::genre, text)
                )
            }
        }.ids
    }

    suspend fun searchDocument(text: String): List<StoryDAO> {
        return client.search("story") {
            query = bool {
                must(
                    match(StoryDAO::genre, text)
                )
            }
        }.hits?.hits?.map {
            deserializeHit(it)
        }.orEmpty()
    }

    private fun deserializeHit(hit: SearchResponse.Hit) = DEFAULT_JSON.decodeFromString(StoryDAO.serializer(), hit.source.toString())

}