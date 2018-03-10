package br.com.aaascp.androidapp.infra.source.remote.body.response

data class LessonResponseBody(
        val id: String,
        val area: AreaResponseBody,
        val title: String
)
