package com.siskadwi.kantor.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.siskadwi.kantor.model.Office
import kotlinx.coroutines.flow.Flow

@Dao
interface OfficeDao {
    @Query("SELECT * FROM office-table ORDER BY name ASC")
    fun getAllOffice():Flow<List<Office>>

    @Insert
    suspend fun insertOfiice(office: Office)

    @Delete
    suspend fun deleteOffice(office: Office)

    @Update fun updateOffice(office: Office)
}