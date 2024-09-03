package com.org.marton.studio.project.eldarwallet.utils

import com.org.marton.studio.project.eldarwallet.domain.model.Bank
import com.org.marton.studio.project.eldarwallet.domain.model.CardBrand
import com.org.marton.studio.project.eldarwallet.domain.model.CardType

object CardUtils {
    fun getBrandCard(cardNumber: Long): CardBrand? {
        val firstDigit =cardNumber.toString().firstOrNull()?.digitToIntOrNull()
        return when (firstDigit) {
            3 -> CardBrand.AMERICAN_EXPRESS
            4 -> CardBrand.VISA
            5 -> CardBrand.MASTERCARD
            else -> null
        }
    }

    fun getBrandCardNameByCode(code: Int): String? {
        return when (code) {
            3 -> CardBrand.AMERICAN_EXPRESS.desc
            4 -> CardBrand.VISA.desc
            5 -> CardBrand.MASTERCARD.desc
            else -> null
        }
    }

    fun getTypeCardDescByCode(code: Int): String? {
        return when (code) {
            1 -> CardType.DEBIT.desc
            2 -> CardType.CREDIT.desc
            else -> null
        }
    }

    fun getBankNameByCode(code: Int): String? {
        return when (code) {
                1-> Bank.SANTANDER.desc
                2-> Bank.GALICIA.desc
                3-> Bank.BBVA.desc
                4-> Bank.CIUDAD.desc
                5-> Bank.NACION.desc
                6-> Bank.MACRO.desc
                7-> Bank.PATAGONIA.desc
                8-> Bank.HSBC.desc
                9-> Bank.ICBC.desc
                10-> Bank.COMAFI.desc
                11-> Bank.SUPERVIELLE.desc
            else -> null
        }
    }
}