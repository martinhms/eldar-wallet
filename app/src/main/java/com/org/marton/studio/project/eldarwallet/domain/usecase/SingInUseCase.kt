package com.org.marton.studio.project.eldarwallet.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.repositories.UserRepository
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SingInUseCase @Inject
constructor(private val repository: UserRepository) {
    @RequiresApi(Build.VERSION_CODES.P)
    operator fun invoke(username: String, password: String) : Flow<UserData?> = repository.getUserByUsernamePassword(username, password)
}