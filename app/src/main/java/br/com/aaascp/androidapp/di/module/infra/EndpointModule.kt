package br.com.aaascp.androidapp.di.module.infra

import android.arch.paging.PagingRequestHelper
import br.com.aaascp.androidapp.infra.source.remote.ServiceGenerator
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import javax.inject.Singleton


@Module
open class EndpointModule {

    @Singleton
    @Provides
    fun providesPagingRequestHelper(executor: Executor): PagingRequestHelper {
        return PagingRequestHelper(executor)
    }

    @Singleton
    @Provides
    open fun providesAreaEndpoint(): AreaEndpoint {
        return ServiceGenerator.createService(AreaEndpoint::class.java)
    }

    @Singleton
    @Provides
    open fun providesLessonEndpoint(): LessonEndpoint {
        return ServiceGenerator.createService(LessonEndpoint::class.java)
    }
}