package com.wordsum.search.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "wordsum")
class WordSumConfig {

    lateinit var index: String
}