package cz.sima.msbank.utils.extensions

/**
 * Created by Michal Šíma on 11.06.2020.
 */
fun String.prependWithCharToLength(char: Char, length: Int): String {
    val builder = StringBuilder(this)
    while (builder.length < length) {
        builder.insert(0, char)
    }
    return builder.toString()
}