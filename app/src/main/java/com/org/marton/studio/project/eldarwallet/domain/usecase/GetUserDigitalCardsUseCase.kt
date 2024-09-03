package com.org.marton.studio.project.eldarwallet.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.repositories.DigitalCardRepository
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDigitalCardsUseCase @Inject
constructor(private val repository: DigitalCardRepository) {
    @RequiresApi(Build.VERSION_CODES.P)
    operator fun invoke(userId: String): Flow<List<DigitalCard>> = repository.getDigitalCard(userId)
}