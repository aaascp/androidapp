package br.com.aaascp.androidapp.infra.source.remote.body.lesson

data class AreaFilter(
        val value: String,
        val modifier: String = "Igual",
        val type: String = "id"
)

data class LessonFilterRequestBody(val area: AreaFilter)