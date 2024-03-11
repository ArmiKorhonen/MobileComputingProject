package com.example.naturediary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DiaryEntry::class], version = 1, exportSchema = false)
abstract class DiaryEntryDatabase : RoomDatabase() {
    abstract fun diaryEntryDAO(): DiaryEntryDAO

    companion object {
        // Singleton prevents multiple instances of the database opening at the same time.
        @Volatile
        private var INSTANCE: DiaryEntryDatabase? = null

        fun getDatabase(context: Context): DiaryEntryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryEntryDatabase::class.java,
                    "diary_entry_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
