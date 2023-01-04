package com.example.apiapp.data.objects.Dao

import com.example.apiapp.data.objects.Post

data class EventPostInformationDto(
    val content: String,
    val createdAt: Long,
    val creator: String,
    val eventId: Int,
    val eventName: String
)

fun EventPostInformationDto.toPost(): Post = Post(content,createdAt)

fun List<EventPostInformationDto>.toPostList(): ArrayList<Post> {
    val list = ArrayList<Post>()
    forEach {
        list.add(it.toPost())
    }
    return list
}