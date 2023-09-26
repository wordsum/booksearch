package com.wordsum.search

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType


@Document(indexName = "story")
data class StoryDAO(
    @Id
    val id: String,
    @Field(store = true, type = FieldType.Text, fielddata = true)
    val name: String,
    @Field(store = true, type = FieldType.Text, fielddata = true)
    val genre: String,
    @Field(store = true, type = FieldType.Text, fielddata = true)
    val type: String,
)

