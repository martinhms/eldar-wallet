package com.org.marton.studio.project.eldarwallet.data.db.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.UserDao
import com.org.marton.studio.project.eldarwallet.data.db.entities.UserEntity
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject
constructor(private val userDao: UserDao) {

    @RequiresApi(Build.VERSION_CODES.P)
    fun getUser(id: String): Flow<UserData> =
        userDao.getUser(id).map { entity->
            entity?.let {
                UserData(
                    userName = entity.userName,
                    userLastname = entity.userLastname,
                    password = entity.password,
                    identification = entity.identification,
                    email = entity.email,
                    avatar = entity.avatar,
                    balance = entity.balance,
                    createdTime = entity.createdTime,
                    updatedTime = entity.updatedTime,
                    deletedTime = entity.deletedTime
                )
            } ?: UserData(
                userName = "",
                userLastname = "",
                password = "",
                identification = "",
                email = "",
                avatar = "",
                balance = 0.0,
                createdTime = 0,
                updatedTime = 0,
                deletedTime = 0
            )
        }

    fun getUserByUsernamePassword(username: String, password: String): Flow<UserData?> =
        userDao.getUserByUsernamePassword(username, password).map { entity->
            entity?.let {
                UserData(
                    id = entity.id,
                    userName = entity.userName,
                    userLastname = entity.userLastname,
                    password = entity.password,
                    identification = entity.identification,
                    email = entity.email,
                    avatar = entity.avatar,
                    balance = entity.balance,
                    createdTime = entity.createdTime,
                    updatedTime = entity.updatedTime,
                    deletedTime = entity.deletedTime
                )
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createUser(userData: UserData) {
        val entity = userData.toData().copy(createdTime = System.currentTimeMillis())
        userDao.createUser(entity)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun UserData.toData(): UserEntity {
    return UserEntity(
        userName = this.userName,
        userLastname = this.userLastname,
        password = this.password,
        identification = this.identification ?: "",
        email = this.email?: "",
        avatar = this.avatar ?: "",
        balance = this.balance,
        createdTime = this.createdTime,
        updatedTime = this.updatedTime,
        deletedTime = this.deletedTime
    )
}
