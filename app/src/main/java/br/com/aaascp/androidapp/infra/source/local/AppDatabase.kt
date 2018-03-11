package br.com.aaascp.androidapp.infra.source.local

import br.com.aaascp.androidapp.infra.source.local.dao.area.AreaLocalDataSource
import br.com.aaascp.androidapp.infra.source.local.dao.lesson.LessonLocalDataSource

interface AppDatabase {
    fun runInTransaction(body: () -> Unit)

    fun areaDao(): AreaLocalDataSource

    fun lessonDao(): LessonLocalDataSource
}