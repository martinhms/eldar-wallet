package com.org.marton.studio.project.eldarwallet.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.org.marton.studio.project.eldarwallet.data.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
@SuppressWarnings("UNCHECKED_CAST")
interface UserDao {

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    fun getUser(id:String): Flow<UserEntity?>

    @Insert
    fun createUser(item: UserEntity)

    @Update
    fun updateUser(item: UserEntity)

    @Delete
    fun deleteUser(item: UserEntity)
}