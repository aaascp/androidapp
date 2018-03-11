package br.com.aaascp.androidapp.infra.source.local.dao.lesson

import android.arch.paging.DataSource
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson


interface LessonLocalDataSource {
    fun getForArea(areaId: String): DataSource.Factory<Int, Lesson>

    fun save(lessons: List<Lesson>)

    fun removeAllForArea(areaId: String)

    fun getNextIndexForArea(areaId: String): Int
}