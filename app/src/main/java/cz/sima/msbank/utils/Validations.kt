package cz.sima.msbank.utils

import cz.sima.msbank.utils.extensions.countDigitRoot

/**
 * Created by Michal Šíma on 07.06.2020.
 */
object Validations {

    fun isCardNumberValid(cardNumber: String): Boolean {
        val numberWithoutSpaces = cardNumber.replace(Regex("\\s+"), "")
        if (numberWithoutSpaces.length != 16) {
            // Card length not valid
            return false
        }

        val chars = numberWithoutSpaces.toCharArray()
        val cardDigits = chars.map(Character::getNumericValue).toMutableList()

        // If containing -1 or -2 -> number contains non digit char (see Character::getNumericValue)
        if (cardDigits.containsAll(listOf(-1, -2))) {
            // Card number containing non digit
            return false
        }

        val evenIndexDigits = cardDigits.filterIndexed { index, _ -> index % 2 == 0 }
        val oddIndexDigits = cardDigits.filterIndexed { index, _ -> index % 2 == 1 }

        val evenDigitsSum = evenIndexDigits
            .map { it * 2 }
            .fold(0, { acc, number -> acc + number.countDigitRoot() })

        val oddDigitsSum = oddIndexDigits
            .fold(0, { acc, number -> acc + number })

        return (evenDigitsSum + oddDigitsSum) % 10 == 0
    }
}