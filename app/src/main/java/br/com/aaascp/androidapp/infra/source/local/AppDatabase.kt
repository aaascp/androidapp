package br.com.aaascp.androidapp.infra.source.local

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.local.dao.AreaDao
import br.com.aaascp.androidapp.infra.source.local.dao.LessonDao
import javax.inject.Singleton


@Singleton
@Database(
        entities = arrayOf(
                Area::class,
                Lesson::class),
        version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun areaDao(): AreaDao
    abstract fun lessonDao() : LessonDao
}