package com.wordsum.search


import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository
import org.springframework.stereotype.Repository

@Repository
interface StoryRepository: ReactiveElasticsearchRepository<StoryDAO, String> {


}