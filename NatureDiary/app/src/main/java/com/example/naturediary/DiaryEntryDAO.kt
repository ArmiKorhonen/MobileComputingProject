package com.example.naturediary

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryEntryDAO {
    @Insert
    suspend fun insert(note: DiaryEntry)

    @Query("SELECT * FROM diary_entry_table ORDER BY timestamp DESC")
    fun getAllEntriesSortedByDate(): Flow<List<DiaryEntry>>

    // Add methods to update or delete notes as needed
}
