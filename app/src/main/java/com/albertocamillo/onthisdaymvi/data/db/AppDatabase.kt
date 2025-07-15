package com.albertocamillo.onthisdaymvi.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.albertocamillo.onthisdaymvi.data.db.AppDatabase.Companion.getDatabase

// ============================
// AppDatabase.kt
// ============================
/**
 * The Room database for the app. It holds the [OnThisDayDao] and is responsible
 * for providing access to the local SQLite database.
 *
 * It's a singleton accessed via [getDatabase] and ensures there's only one instance
 * per app lifecycle. It stores JSON responses in [OnThisDayEntity] format.
 *
 * Room handles migrations and lifecycle automatically. This file binds the DAO interface
 * to the database.
 */

@Database(entities = [OnThisDayEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun onThisDayDao(): OnThisDayDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "on_this_day.db"
                ).build().also { INSTANCE = it }
            }
    }
}