package com.wordsum.search

import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration

import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration


@Configuration
class SearchConfig : ReactiveElasticsearchConfiguration() {
    override fun clientConfiguration(): ClientConfiguration {
        return ClientConfiguration.builder()
            .connectedTo("localhost:9200")
            .build()
    }
}