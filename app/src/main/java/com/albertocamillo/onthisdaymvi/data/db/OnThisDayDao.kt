package com.albertocamillo.onthisdaymvi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface OnThisDayDao {
    @Query("SELECT * FROM on_this_day WHERE date = :date")
    suspend fun getByDate(date: String): OnThisDayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: OnThisDayEntity)
}