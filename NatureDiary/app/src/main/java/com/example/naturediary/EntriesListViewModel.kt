/**
 * File: EntriesListViewModel.kt
 *
 * Description: This ViewModel is responsible for managing UI-related data for the entries list screen
 * in the application. It leverages dependency injection to obtain an instance of DiaryEntryDAO for
 * accessing the database. The ViewModel provides functionality to observe all diary entries sorted by
 * date, insert new diary entries into the database, and retrieve specific entries by their ID. The
 * LiveData observed by the UI layer allows for dynamic and responsive updates to the user interface
 * based on changes in the database content.
 */

package com.example.naturediary

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EntriesListViewModel @Inject constructor(private val diaryEntryDAO: DiaryEntryDAO) : ViewModel() {

    val entries = diaryEntryDAO.getAllEntriesSortedByDate().asLiveData()

    fun insertEntry(diaryEntry: DiaryEntry) {
        viewModelScope.launch {
            diaryEntryDAO.insert(diaryEntry)
        }
    }

    fun getEntryById(entryId: Int): LiveData<DiaryEntry> {
        return diaryEntryDAO.getEntryById(entryId)
    }

}

