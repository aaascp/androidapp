package br.com.aaascp.androidapp.infra.source.remote.body.lesson

import br.com.aaascp.androidapp.infra.source.remote.body.area.AreaResponseBody

data class LessonResponseBody(
        val id: String,
        val area: AreaResponseBody,
        val title: String
)
