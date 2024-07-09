package com.example.trackerexodon.data.model

import androidx.compose.runtime.MutableState
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_table")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val amount: String,
    val date: String,
    val category: String,
    val type: String
)
