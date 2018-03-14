package br.com.aaascp.androidapp.di.module.infra

import android.app.Application
import android.arch.persistence.room.Room
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.area.AreaRepository
import br.com.aaascp.androidapp.infra.source.local.dao.area.AreaLocalDataSource
import br.com.aaascp.androidapp.infra.repository.area.AreaWithLocalDataRepository
import br.com.aaascp.androidapp.infra.repository.lesson.LessonRepository
import br.com.aaascp.androidapp.infra.source.local.dao.lesson.LessonLocalDataSource
import br.com.aaascp.androidapp.infra.repository.lesson.LessonWithLocalDataRepository
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.RoomDatabase
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import dagger.Module
import dagger.Provides
import java.util.concurrent.*
import javax.inject.Singleton

@Module
class DatabaseMockModule(application: Application) : DatabaseModule(application){
    override fun name() = "app-dev-db.db"
}