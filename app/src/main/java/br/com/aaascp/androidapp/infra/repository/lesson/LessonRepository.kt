package br.com.aaascp.androidapp.infra.repository.lesson

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson

interface LessonRepository {
    fun getAllForArea(areaId: String): LiveData<List<Lesson>>
}