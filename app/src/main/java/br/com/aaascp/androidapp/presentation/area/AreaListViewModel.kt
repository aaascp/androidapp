package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.MainApplication
import br.com.aaascp.androidapp.infra.repository.area.AreaRepository
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import javax.inject.Inject


class AreaListViewModel : ViewModel() {

    @Inject
    lateinit var areaRepository: AreaRepository

    var areas: LiveData<List<Area>>

    init {
        MainApplication.component.inject(this)
        areas = areaRepository.getAll()
    }
}
