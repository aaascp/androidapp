package br.com.aaascp.androidapp.infra.source.remote.endpoint

import br.com.aaascp.androidapp.infra.source.remote.body.AreaResponse
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

class AreaMockEndpoint(
        private val delegate: BehaviorDelegate<AreaEndpoint>
) : AreaEndpoint {

    override fun getAll(
            start: Int,
            end: Int
    ): Call<AreaResponse> {

        val data = FakeData().getAreas(start, end)

        return delegate
                .returningResponse(data)
                .getAll()
    }
}