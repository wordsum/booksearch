package com.wordsum.search.controller

import com.wordsum.search.database.ElasticsearchDao
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Controller
class IndexController (
    private val elasticsearchDao: ElasticsearchDao
){

    @MutationMapping
    suspend fun putIndex(@Argument indexInput: String): Mono<String> {
        return elasticsearchDao.createIndex(indexInput).toMono()
    }

    @QueryMapping
    suspend fun getIndex(@Argument indexInput: String): Mono<Boolean> {
        return elasticsearchDao.getIndex(indexInput).containsKey(indexInput).toMono()
    }

    @MutationMapping
    suspend fun deleteIndex(@Argument indexInput: String): Mono<String> {
        return elasticsearchDao.deleteIndex(indexInput).toMono()
    }

}