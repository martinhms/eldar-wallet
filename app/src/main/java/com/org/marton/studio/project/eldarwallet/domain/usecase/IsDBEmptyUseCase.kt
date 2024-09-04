package com.org.marton.studio.project.eldarwallet.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsDBEmptyUseCase @Inject
constructor(private val repository: UserRepository) {

    @RequiresApi(Build.VERSION_CODES.P)
    operator fun invoke(): Flow<Int> = repository.getUsersCount()

}