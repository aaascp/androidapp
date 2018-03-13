package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.LessonResponse
import retrofit2.Call
import retrofit2.http.*

interface LessonEndpoint {

    companion object {
        const val NETWORK_PAGE_SIZE: Int = 25
    }

    @GET("/v2/admin/lessons")
    fun getForArea(
            @Query("filter") lessonFilterRequestBody: String,
            @Query("start") start: Int = 0,
            @Query("end") end: Int = NETWORK_PAGE_SIZE
    ): Call<LessonResponse>
}