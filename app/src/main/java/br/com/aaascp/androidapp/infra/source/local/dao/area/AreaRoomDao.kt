package br.com.aaascp.androidapp.infra.source.local.dao.area

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import br.com.aaascp.androidapp.infra.source.local.entity.Area

@Dao
interface AreaRoomDao : AreaLocalDataSource {

    @Query("SELECT * from Area")
    override fun getAll(): DataSource.Factory<Int, Area>

    @Insert(onConflict = REPLACE)
    override fun save(areas: List<Area>)

    @Query("DELETE FROM Area")
    override fun removeAll()

    @Query("SELECT MAX(indexInResponse) + 1 FROM Area")
    override fun getNextIndex() : Int
}
