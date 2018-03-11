package br.com.aaascp.androidapp.di.module.infra

import android.app.Application
import android.arch.persistence.room.Room
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
class DatabaseModule(application: Application) {

    private val database: RoomDatabase

    companion object {
        const val DATABASE_NAME = "app-db.db"
    }

    init {
        database = Room.databaseBuilder(
                application,
                RoomDatabase::class.java,
                DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun providesDatabase(): AppDatabase {
        return database
    }

    @Singleton
    @Provides
    fun providesExecutor(): Executor {
        return ThreadPoolExecutor(
                2,
                2,
                0,
                TimeUnit.MILLISECONDS,
                LinkedBlockingQueue<Runnable>())
    }

    @Singleton
    @Provides
    fun providesAreaDataSource(database: AppDatabase): AreaLocalDataSource {
        return database.areaDao()
    }

    @Singleton
    @Provides
    fun providesLessonDataSource(database: AppDatabase): LessonLocalDataSource {
        return database.lessonDao()
    }

    @Singleton
    @Provides
    fun areaRepository(
            areaEndpoint: AreaEndpoint,
            areaLocalDataSource: AreaLocalDataSource): AreaRepository {

        return AreaWithLocalDataRepository(
                areaEndpoint,
                areaLocalDataSource)
    }

    @Singleton
    @Provides
    fun lessonRepository(
            lessonEndpoint: LessonEndpoint,
            lessonLocalDataSource: LessonLocalDataSource): LessonRepository {

        return LessonWithLocalDataRepository(
                lessonEndpoint,
                lessonLocalDataSource)
    }
}