package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.AreaResponse
import br.com.aaascp.androidapp.util.AndroidUtils
import retrofit2.Call
import retrofit2.Response

class AreaMockEndpoint : AreaEndpoint {
    override fun getAll(
            start: Int,
            end: Int
    ): Call<AreaResponse> {

        return CallMock<AreaResponse>(
                Response.success(FakeData().getAreas(start,end)),
                AndroidUtils().isOnline())
    }

}