package com.wordsum.search

import com.jillesvangurp.ktsearch.search
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import org.springframework.graphql.data.method.annotation.Argument
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@Controller
class StoryController(
    private val storyRepository: StoryRepository,
    private val elasticClient: ElasticClient,
){

    @QueryMapping
    fun getStories(): Flux<StoryDAO> {
        return storyRepository.findAll()
    }

    @QueryMapping
    fun getStory(@Argument id: String): Mono<StoryDAO> {
        return storyRepository.findById(id)
    }

    @QueryMapping
    suspend fun searchStoryIds(@Argument text: String): Flux<String> {
        return elasticClient.searchDocumentIds(text).toFlux()
    }

    @QueryMapping
    suspend fun searchStory(@Argument text: String): Flux<StoryDAO> {
        return elasticClient.searchDocument(text).toFlux()
    }

    @MutationMapping
    fun putStory(@Argument storyDAO: StoryDAO): Mono<StoryDAO> {
        return storyRepository.save(storyDAO)
    }

    @MutationMapping
    fun deleteStory(@Argument id: String) {
        storyRepository.deleteById(id).subscribe()
    }

}