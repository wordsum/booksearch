package com.wordsum.search

import kotlinx.serialization.Serializable
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Serializable
@Document(indexName = "story")
data class StoryDAO(
    @Id
    val id: String,
    @Field(store = true, type = FieldType.Text)
    val name: String,
    @Field(store = true, type = FieldType.Text)
    val genre: String,
    @Field(store = true, type = FieldType.Text)
    val type: String,
    @Field(store = true, type = FieldType.Integer)
    val wordCount: Int?,
    @Field(store = true, type = FieldType.Text)
    val summary: String?,
)

