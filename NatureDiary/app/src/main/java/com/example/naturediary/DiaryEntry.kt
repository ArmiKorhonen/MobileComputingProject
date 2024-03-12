/**
 * File: DiaryEntry.kt
 *
 * Description: Defines the data model for a diary entry within the application. This class is annotated
 * with @Entity to represent a SQLite table. Each instance of DiaryEntry corresponds to a row in the
 * database table, with fields representing the column values. The primary key is autogenerated. The
 * model includes properties for the note's content, geographic coordinates (latitude, longitude),
 * address, timestamp of the entry, and the temperature at the time of entry creation.
 */

package com.example.naturediary

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "diary_entry_table")
data class DiaryEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val note: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val timestamp: Long,
    val temperature: Double
)
