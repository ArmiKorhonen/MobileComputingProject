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

