package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations.map
import android.arch.lifecycle.Transformations.switchMap
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.area.AreaRepository
import javax.inject.Inject


class AreaListViewModel : ViewModel() {

    @Inject
    lateinit var repository: AreaRepository

    private var start = MutableLiveData<Boolean>()

    init {
        MainApplication.component.inject(this)
        this.start.value = true
    }

    private val repositoryResult = map(start, {
        repository.getAll()
    })

    val areas = switchMap(repositoryResult, { it.pagedList })!!
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

