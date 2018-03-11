package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.lesson.LessonResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LessonEndpoint {

    @GET("/v2/admin/lessons?filter=")
    fun getAllForArea(
            @Query("area") lessonFilterRequestBody: String
    ): Call<DataResponseBody<List<LessonResponseBody>>>
}