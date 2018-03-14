package br.com.aaascp.androidapp.infra.source.local

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.local.dao.area.AreaRoomDao
import br.com.aaascp.androidapp.infra.source.local.dao.lesson.LessonRoomDao
import javax.inject.Singleton


@Singleton
@Database(
        entities = arrayOf(
                Area::class,
                Lesson::class),
        version = 3)
abstract class RoomDatabase : RoomDatabase(), AppDatabase {

    override fun runInTransaction(body: () -> Unit) {
        super.runInTransaction(body)
    }

    abstract override fun areaDao(): AreaRoomDao

    abstract override fun lessonDao(): LessonRoomDao
}