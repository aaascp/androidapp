package br.com.aaascp.androidapp.infra.source.local

import br.com.aaascp.androidapp.infra.source.local.dao.area.AreaRoomDao
import br.com.aaascp.androidapp.infra.source.local.dao.lesson.LessonRoomDao

interface AppDatabase {
    fun areaDao(): AreaRoomDao

    fun lessonDao(): LessonRoomDao
}