package com.org.marton.studio.project.eldarwallet.data.db.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.DigitalCardDao
import com.org.marton.studio.project.eldarwallet.data.db.entities.DigitalCardEntity
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DigitalCardRepository @Inject
constructor(private val digitalCardDao: DigitalCardDao) {

    @RequiresApi(Build.VERSION_CODES.P)
    fun getDigitalCard(clientId: String): Flow<List<DigitalCard>> =
        digitalCardDao.getDigitalCard(clientId = clientId).map { items ->
            items.map {
                DigitalCard(
                    number = it.number,
                    ownerClientId = it.ownerClientId,
                    type = it.type,
                    bank = it.bank,
                    brand = it.brand,
                    securityCode = it.securityCode,
                    expirationDate = it.expirationDate
                )
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addDigitalCard(digitalCard: DigitalCard) {
        val entity = digitalCard.toData()
        digitalCardDao.addDigitalCard(entity)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun DigitalCard.toData(): DigitalCardEntity {
    return DigitalCardEntity(
        number = this.number,
        ownerClientId = this.ownerClientId,
        bank = this.bank,
        brand = this.brand,
        type = this.type,
        expirationDate = this.expirationDate,
        securityCode = this.securityCode
    )
}
