package com.org.marton.studio.project.eldarwallet.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.repositories.UserRepository
import com.org.marton.studio.project.eldarwallet.ui.models.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserDataUseCase @Inject
constructor(private val repository: UserRepository) {
    @RequiresApi(Build.VERSION_CODES.P)
   suspend operator fun invoke(userId: UserData) {
        repository.createUser(userId)
    }
}