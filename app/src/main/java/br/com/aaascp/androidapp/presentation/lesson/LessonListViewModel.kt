package br.com.aaascp.androidapp.presentation.lesson

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.lesson.LessonRepository
import javax.inject.Inject

class LessonListViewModel : ViewModel() {

    @Inject
    lateinit var repository: LessonRepository

    init {
        MainApplication.component.inject(this)
    }

    fun showLessonsForArea(areaId: String) {
        this.areaId.value = areaId
    }

    private val areaId = MutableLiveData<String>()
    private val repositoryResult = map(areaId, {
        repository.getForArea(it)
    })

    val lessons = switchMap(repositoryResult, { it.pagedList })!!
    val networkState = switchMap(repositoryResult, { it.networkState })!!
    val refreshState = switchMap(repositoryResult, { it.refreshState })!!

    fun refresh() {
        repositoryResult.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = repositoryResult?.value
        listing?.retry?.invoke()
    }
}