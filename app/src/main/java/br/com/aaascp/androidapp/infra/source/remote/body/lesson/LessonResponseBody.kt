package br.com.aaascp.androidapp.infra.source.remote.body.lesson

data class Area(val id: String)

data class LessonResponseBody(
        val id: String,
        val area: Area,
        val title: String
)