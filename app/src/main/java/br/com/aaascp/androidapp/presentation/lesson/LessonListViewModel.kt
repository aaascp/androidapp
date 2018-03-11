package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import br.com.aaascp.androidapp.infra.repository.LessonRepository
import javax.inject.Inject

class LessonListViewModel : ViewModel() {

    @Inject
    lateinit var lessonRepository: LessonRepository

    var lessons: LiveData<List<Lesson>>? = null

    init {
        MainApplication.component.inject(this)
    }

    fun getLessonsForArea(areaId: String) {
        if (this.lessons != null) {
            return
        }
        lessons = lessonRepository.getAllForArea(areaId)
    }
}