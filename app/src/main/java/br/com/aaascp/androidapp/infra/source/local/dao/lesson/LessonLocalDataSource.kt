package br.com.aaascp.androidapp.infra.source.local.dao.lesson

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson

interface LessonLocalDataSource {
    fun getAllForArea(areaId: String): LiveData<List<Lesson>>

    fun save(lessons: List<Lesson>)

    fun removeAllForArea(areaId: String)
}