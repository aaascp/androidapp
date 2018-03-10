package br.com.aaascp.androidapp.infra.source.local

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import br.com.aaascp.androidapp.domain.entity.Lesson
import br.com.aaascp.androidapp.domain.entity.Area
import br.com.aaascp.androidapp.infra.source.local.dao.AreaDao
import br.com.aaascp.androidapp.infra.source.local.dao.LessonDao


@Database(
        entities = arrayOf(
                Area::class,
                Lesson::class),
        version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun areaDao(): AreaDao
    abstract fun lessonDao() : LessonDao
}