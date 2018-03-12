package br.com.aaascp.androidapp.di.module.infra

import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaMockEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonMockEndpoint

class EndpointMockModule : EndpointModule() {

    override fun providesAreaEndpoint(): AreaEndpoint {
        return AreaMockEndpoint()
    }

    override fun providesLessonEndpoint(): LessonEndpoint {
        return LessonMockEndpoint()
    }
}