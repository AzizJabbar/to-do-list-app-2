package com.dicoding.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "startTime")
    val startTimeMillis: Long,

    @ColumnInfo(name = "endTime")
    val endTimeMillis: Long,

    @ColumnInfo(name = "completed")
    val isCompleted: Boolean = false
)
