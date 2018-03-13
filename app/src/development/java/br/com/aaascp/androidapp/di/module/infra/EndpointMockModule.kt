package br.com.aaascp.androidapp.di.module.infra

import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaMockEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonMockEndpoint
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

class EndpointMockModule : EndpointModule() {

    override fun providesAreaEndpoint(): AreaEndpoint {
        return AreaMockEndpoint()
    }

    override fun providesLessonEndpoint(): LessonEndpoint {
        mockRetrofit.create(LessonEndpoint::class.java)
        return LessonMockEndpoint(mockRetrofit.create(LessonEndpoint::class.java))
    }

    private val mockRetrofit =
            MockRetrofit.Builder(ServiceGenerator.retrofit)
                    .networkBehavior(NetworkBehavior.create())
                    .build()

}