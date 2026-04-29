package com.gramasuvidha.portal.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feedback")
data class FeedbackEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val projectId: Int,
    val userName: String,
    val comment: String,
    val rating: Int,
    val timestamp: Long = System.currentTimeMillis()
)
