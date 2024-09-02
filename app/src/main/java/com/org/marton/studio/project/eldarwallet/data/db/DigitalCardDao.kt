package com.org.marton.studio.project.eldarwallet.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.org.marton.studio.project.eldarwallet.data.db.entities.DigitalCardEntity
import com.org.marton.studio.project.eldarwallet.data.db.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
@SuppressWarnings("UNCHECKED_CAST")
interface DigitalCardDao {

    @Query("SELECT * FROM DigitalCardEntity WHERE ownerClientId = :clientId")
    fun getDigitalCard(clientId:String): Flow<List<DigitalCardEntity>>

    @Insert
    fun addDigitalCard(digitalCardEntity: DigitalCardEntity)

}