package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.lesson.LessonRepository
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson
import javax.inject.Inject

class LessonListViewModel : ViewModel() {

    @Inject
    lateinit var lessonRepository: LessonRepository

    lateinit var lessons: LiveData<PagedList<Lesson>>

    init {
        MainApplication.component.inject(this)
    }

    fun getLessonsForArea(areaId: String) {
        lessons = lessonRepository.getForArea(areaId).pagedList
    }
}