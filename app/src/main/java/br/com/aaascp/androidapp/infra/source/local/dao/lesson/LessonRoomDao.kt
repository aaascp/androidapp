package br.com.aaascp.androidapp.infra.source.local.dao.lesson

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.aaascp.androidapp.infra.source.local.entity.Lesson

@Dao
interface LessonRoomDao : LessonLocalDataSource {

    @Query("SELECT * FROM Lesson WHERE areaId = :areaId")
    override fun getAllForArea(areaId: String): LiveData<List<Lesson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun save(lessons: List<Lesson>)

    @Query("DELETE FROM Lesson WHERE areaId = :areaId")
    override fun removeAllForArea(areaId: String)
}