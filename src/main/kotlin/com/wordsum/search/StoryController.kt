package com.wordsum.search

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
class StoryController(private val storyRepository: StoryRepository){

    @QueryMapping
    fun stories(): Flux<StoryDAO> {
        return storyRepository.findAll()
    }

    @QueryMapping
    fun story(id: String): Mono<StoryDAO> {
        return storyRepository.findById(id)
    }


}