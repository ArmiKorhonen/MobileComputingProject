package com.example.naturediary

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiaryEntryDAO {
    @Insert
    suspend fun insert(note: DiaryEntry)

    @Query("SELECT * FROM diary_entry_table")
    fun getAllEntries(): LiveData<List<DiaryEntry>>

    // Add methods to update or delete notes as needed
}
