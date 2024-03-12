/**
 * File: DiaryEntryDatabase.kt & DatabaseModule.kt
 *
 * Description: These files collectively define the Room database setup for the application. DiaryEntryDatabase
 * is an abstract class that extends RoomDatabase, providing an abstract DAO method. It includes a singleton
 * pattern to prevent multiple instances of the database from being opened simultaneously.
 *
 * The DatabaseModule, annotated with @Module and @InstallIn, provides Dagger Hilt dependency injection setup
 * for the database and DAO. It ensures that the application context is used to create a singleton instance of
 * the database and DAO, which are provided to the app components as needed.
 */

package com.example.naturediary

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDiaryEntryDatabase(@ApplicationContext appContext: Context): DiaryEntryDatabase {
        return Room.databaseBuilder(
            appContext,
            DiaryEntryDatabase::class.java,
            "diary_entry_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDiaryEntryDAO(database: DiaryEntryDatabase): DiaryEntryDAO {
        return database.diaryEntryDAO()
    }
}


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
