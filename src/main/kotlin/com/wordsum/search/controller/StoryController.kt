package com.wordsum.search.controller

import com.wordsum.search.database.ElasticsearchDao
import com.wordsum.search.model.Story
import com.wordsum.search.model.StoryInput
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.graphql.data.method.annotation.Argument
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono

@Controller
class StoryController(
    private val elasticsearchDao: ElasticsearchDao
){

    @QueryMapping
    suspend fun getStories(): Flux<String> {
        return elasticsearchDao.matchAll().toFlux()
    }

    @QueryMapping
    suspend fun getStory(@Argument id: String): Mono<String> {
        return elasticsearchDao.matchId(id).toMono()
    }

    @QueryMapping
    suspend fun searchStoryIds(@Argument text: String): Flux<String> {
        return elasticsearchDao.searchDocumentIds(text).toFlux()
    }

    @QueryMapping
    suspend fun searchStory(@Argument text: String): Flux<Story> {
        return elasticsearchDao.searchDocument(text).toFlux()
    }

    @MutationMapping
    suspend fun putStory(@Argument storyInput: StoryInput): Mono<String> {
        return elasticsearchDao.addStory(storyInput).toMono()
    }

    @MutationMapping
    suspend fun deleteStory(@Argument id: String): Mono<String> {
        return elasticsearchDao.deleteStory(id).toMono()
    }

}