package br.com.aaascp.androidapp.infra.source.remote.body

import br.com.aaascp.androidapp.infra.source.remote.body.area.AreaResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonResponseBody

typealias LessonResponse = DataResponseBody<List<LessonResponseBody>>
typealias AreaResponse = DataResponseBody<List<AreaResponseBody>>