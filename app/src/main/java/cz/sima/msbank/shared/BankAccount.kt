package cz.sima.msbank.shared

import cz.sima.msbank.utils.Validations.MAX_NUMBER_LENGTH
import cz.sima.msbank.utils.Validations.MAX_PREFIX_LENGTH
import cz.sima.msbank.utils.extensions.prependWithCharToLength

/**
 * Created by Michal Šíma on 11.06.2020.
 */
data class BankAccount(
    val prefix: String = "",
    val number: String = "",
    val bankCode: String = ""
) {

    companion object {
        fun fromString(string: String): BankAccount {
            // TODO extend for prefix and create tests
            val split = string.split('/')
            return BankAccount(number = split[0], bankCode = split[1])
        }
    }

    fun toFormattedString(): String {
        val stringBuilder = StringBuilder()

        if (prefix.isNotEmpty()) {
            stringBuilder
                .append(prefix)
                .append("-")
        }

        stringBuilder
            .append(number)
            .append("/")
            .append(bankCode)

        return stringBuilder.toString()
    }

    fun toInstanceWithZeros(): BankAccount {
        return BankAccount(
            prefix.prependWithCharToLength('0', MAX_PREFIX_LENGTH),
            number.prependWithCharToLength('0', MAX_NUMBER_LENGTH),
            bankCode
        )
    }
}
