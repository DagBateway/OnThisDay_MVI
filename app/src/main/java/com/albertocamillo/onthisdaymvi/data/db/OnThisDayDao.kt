package com.albertocamillo.onthisdaymvi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// ============================
// OnThisDayDao.kt
// ============================
/**
 * DAO (Data Access Object) interface for interacting with the local cache of Wikimedia events.
 *
 * It provides methods to get data by date ([getByDate]) and to insert new records ([insert]).
 * Used by the ViewModel to decide whether to load from cache or network.
 */

@Dao
interface OnThisDayDao {
    @Query("SELECT * FROM on_this_day WHERE date = :date")
    suspend fun getByDate(date: String): OnThisDayEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: OnThisDayEntity)
}