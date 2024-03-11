package com.example.naturediary

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary_entry_table")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val note: String,
    val latitude: Double,
    val longitude: Double,
    val address: String, // Ensure you've added this previously
    val timestamp: Long // New field to store date and time
)
