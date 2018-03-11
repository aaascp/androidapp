package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LessonEndpoint {

    @GET("/v2/admin/lessons")
    fun getAllForArea(
            @Query("filter") lessonFilterRequestBody: String
    ): Call<DataResponseBody<List<LessonResponseBody>>>
}