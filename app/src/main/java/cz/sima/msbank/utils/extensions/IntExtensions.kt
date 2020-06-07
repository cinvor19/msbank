package cz.sima.msbank.utils.extensions

import kotlin.math.abs

/**
 * Created by Michal Šíma on 07.06.2020.
 */

/**
 * Count digit root for a number
 *
 * 164 -> 1 + 6 + 4 = 11
 * 48 -> 4 + 8 = 12
 *
 */
fun Int.countDigitRoot(): Int {
    var tmpNumber = abs(this)
    val digits = mutableListOf<Int>()
    while (tmpNumber > 0) {
        digits.add(tmpNumber % 10)
        tmpNumber /= 10
    }

    return digits.sum()
}