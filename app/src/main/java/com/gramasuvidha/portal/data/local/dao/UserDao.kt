package com.gramasuvidha.portal.data.local.dao

import androidx.room.*
import com.gramasuvidha.portal.data.local.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)
}
