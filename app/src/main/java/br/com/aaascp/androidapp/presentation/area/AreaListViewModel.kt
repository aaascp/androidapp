package br.com.aaascp.androidapp.presentation.area

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import br.com.aaascp.androidapp.domain.entity.Area
import br.com.aaascp.androidapp.infra.repository.AreaRepository


class AreaListViewModel : ViewModel() {

    var areas: LiveData<List<Area>>? = null

    fun init(areaRepository: AreaRepository) {
        if (this.areas != null) {
            return
        }
        areas = areaRepository.getSubjectList()
    }
}
