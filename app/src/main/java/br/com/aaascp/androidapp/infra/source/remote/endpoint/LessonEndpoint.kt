package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.response.DataResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.LessonResponseBody
import retrofit2.Call
import retrofit2.http.*

interface LessonEndpoint {

    @GET("/v2/admin/lessons?filter=")
    fun getAllForArea(
            @Query("area") lessonFilterRequestBody: String
    ): Call<DataResponseBody<List<LessonResponseBody>>>
}