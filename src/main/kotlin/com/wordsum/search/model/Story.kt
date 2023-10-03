package com.wordsum.search.model

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val id: String,
    val name: String,
    val genre: String,
    val type: String,
    val wordCount: Int,
    val summary: String? = null,
    )