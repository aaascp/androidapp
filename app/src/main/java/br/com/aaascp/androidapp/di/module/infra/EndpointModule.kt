package br.com.aaascp.androidapp.di.module.infra

import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class EndpointModule {

    @Singleton
    @Provides
    fun providesAreaEndpoint(): AreaEndpoint {
        return ServiceGenerator.createService(AreaEndpoint::class.java)
    }

    @Singleton
    @Provides
    fun providesLessonEndpoint(): LessonEndpoint {
        return ServiceGenerator.createService(LessonEndpoint::class.java)
    }
}