/**
 * File: DiaryEntryDAO.kt
 *
 * Description: This file defines the Data Access Object (DAO) for the DiaryEntry entities. It is an
 * interface annotated with @Dao, providing the necessary methods to access the database. These methods
 * include inserting a new diary entry and querying the database for all entries or a specific entry by
 * its ID. The results are observed through LiveData and Flow, enabling reactive UI updates.
 */

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

    @Query("SELECT * FROM diary_entry_table WHERE id = :entryId")
    fun getEntryById(entryId: Int): LiveData<DiaryEntry>

}
