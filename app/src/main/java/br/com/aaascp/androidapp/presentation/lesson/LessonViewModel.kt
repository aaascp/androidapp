package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.domain.entity.Lesson
import br.com.aaascp.androidapp.infra.repository.LessonRepository

class LessonViewModel : ViewModel() {
    var lessons: LiveData<List<Lesson>>? = null

    fun init(
            lessonRepository: LessonRepository,
            areaId: String) {
        if (this.lessons != null) {
            return
        }
        lessons = lessonRepository.getAllForArea(areaId)
    }
}