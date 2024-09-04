package com.org.marton.studio.project.eldarwallet.data.db.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import com.org.marton.studio.project.eldarwallet.data.db.DigitalCardDao
import com.org.marton.studio.project.eldarwallet.data.db.entities.DigitalCardEntity
import com.org.marton.studio.project.eldarwallet.ui.models.DigitalCard
import com.org.marton.studio.project.eldarwallet.utils.EncryptionHelper
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
                    number = EncryptionHelper.decryptData(it.number).toString(),
                    ownerClientId = it.ownerClientId,
                    type = it.type,
                    bank = it.bank,
                    brand = it.brand,
                    securityCode = EncryptionHelper.decryptData(it.securityCode).toString(),
                    expirationDate = it.expirationDate
                )
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addDigitalCard(digitalCard: DigitalCard) {
        val encryptedCardNumber = EncryptionHelper.encryptData(digitalCard.number.toLong())
        val encryptedSecurityCode = EncryptionHelper.encryptData(digitalCard.securityCode.toLong())

        val entity = digitalCard.toData(encryptedCardNumber, encryptedSecurityCode)
        digitalCardDao.addDigitalCard(entity)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun DigitalCard.toData(
    encryptedCardNumber: String,
    encryptedSecurityCode: String
): DigitalCardEntity {
    return DigitalCardEntity(
        number = encryptedCardNumber,
        ownerClientId = this.ownerClientId,
        bank = this.bank,
        brand = this.brand,
        type = this.type,
        expirationDate = this.expirationDate,
        securityCode = encryptedSecurityCode,
    )
}
