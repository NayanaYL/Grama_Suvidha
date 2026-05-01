package com.gramasuvidha.portal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gramasuvidha.portal.data.local.dao.FeedbackDao
import com.gramasuvidha.portal.data.local.dao.ProjectDao
import com.gramasuvidha.portal.data.local.entities.Feedback
import com.gramasuvidha.portal.data.local.entities.ProjectEntity

@Database(entities = [ProjectEntity::class, Feedback::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun feedbackDao(): FeedbackDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "grama_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
