package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.response.AreaResponseBody
import br.com.aaascp.androidapp.infra.source.remote.body.response.DataResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaEndpoint {

    @GET("/v2/admin/areas")
    fun getAll(
            @Query("start") start: Int  = 0,
            @Query("end") end: Int = 999
    ): Call<DataResponseBody<List<AreaResponseBody>>>
}