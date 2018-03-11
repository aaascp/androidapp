package br.com.aaascp.androidapp.di

import br.com.aaascp.androidapp.di.module.ApplicationModule
import br.com.aaascp.androidapp.di.module.infra.DatabaseModule
import br.com.aaascp.androidapp.di.module.infra.EndpointModule
import br.com.aaascp.androidapp.presentation.area.AreaListViewModel
import br.com.aaascp.androidapp.presentation.lesson.LessonListViewModel
import dagger.Component
import java.util.concurrent.Executor
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                DatabaseModule::class,
                EndpointModule::class))
interface AppComponent {

    fun inject(areaListViewModel: AreaListViewModel)

    fun inject(lessonListViewModel: LessonListViewModel)

    fun getExecutor(): Executor
}