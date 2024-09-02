package com.org.marton.studio.project.eldarwallet.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.repositories.DigitalCardRepository
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import javax.inject.Inject

class AddDigitalCardUseCase @Inject
constructor(private val repository: DigitalCardRepository) {
    @RequiresApi(Build.VERSION_CODES.P)
    suspend operator fun invoke(digitalCard: DigitalCard) {
        repository.addDigitalCard(digitalCard)
    }
}