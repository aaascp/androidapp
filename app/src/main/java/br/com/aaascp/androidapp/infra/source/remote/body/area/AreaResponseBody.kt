package br.com.aaascp.androidapp.infra.source.remote.body.area

data class Subject(val title: String)

data class AreaResponseBody(
        val id: String,
        val title: String,
        val subject: Subject
)