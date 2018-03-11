package br.com.aaascp.androidapp.infra.repository.area

import android.arch.lifecycle.LiveData
import br.com.aaascp.androidapp.infra.adapter.AreaAdapter
import br.com.aaascp.androidapp.infra.repository.RepositoryCallbackBase
import br.com.aaascp.androidapp.infra.source.local.AppDatabase
import br.com.aaascp.androidapp.infra.source.local.entity.Area
import br.com.aaascp.androidapp.infra.source.remote.endpoint.AreaEndpoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AreaWithLocalDataRepository @Inject constructor(
        private val db: AppDatabase,
        private val endpoint: AreaEndpoint
) : AreaRepository {

    override fun getAll(): LiveData<List<Area>> {
        refreshAreaList()
        return db.areaDao().getAll()
    }

    private fun refreshAreaList() {
        endpoint.getAll().enqueue(
                RepositoryCallbackBase({
                    db.runInTransaction {
                        db.areaDao().removeAll()
                        db.areaDao().save(AreaAdapter.adapt(it.data))
                    }
                }))
    }
}

