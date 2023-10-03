package com.wordsum.search.config

import com.jillesvangurp.ktsearch.KtorRestClient
import com.jillesvangurp.ktsearch.SearchClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "elasticsearch")
class ElasticsearchConfig {

    lateinit var host: String
    lateinit var port: String

    fun reactiveSearchClient(): SearchClient {
        return SearchClient(KtorRestClient(host, port.toInt()))
    }

}