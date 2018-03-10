package br.com.aaascp.androidapp.di.module.infra

import android.app.Application
import android.arch.persistence.room.Room
import br.com.aaascp.androidapp.infra.repository.AreaRepository
import br.com.aaascp.androidapp.infra.repository.LessonRepository
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.dao.AreaDao
import br.com.aaascp.androidapp.infra.source.local.dao.LessonDao
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import br.com.aaascp.androidapp.infra.source.remote.endpoint.LessonEndpoint
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DatabaseModule(application: Application) {

    private val database: AppDatabase

    companion object {
        const val DATABASE_NAME = "app-db.db"
    }

    init {
        database = Room.databaseBuilder(
                application,
                AppDatabase::class.java,
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
    fun providesAreaDao(database: AppDatabase): AreaDao {
        return database.areaDao()
    }

    @Singleton
    @Provides
    fun providesLessonDao(database: AppDatabase): LessonDao {
        return database.lessonDao()
    }

    @Singleton
    @Provides
    fun areaRepository(
            areaEndpoint: AreaEndpoint,
            areaDao: AreaDao,
            executor: Executor): AreaRepository {

        return AreaRepository(
                areaEndpoint,
                areaDao,
                executor
        )
    }

    @Singleton
    @Provides
    fun lessonRepository(
            lessonEndpoint: LessonEndpoint,
            lessonDao: LessonDao,
            executor: Executor): LessonRepository {

        return LessonRepository(
                lessonEndpoint,
                lessonDao,
                executor
        )
    }
}