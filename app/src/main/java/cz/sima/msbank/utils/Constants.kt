package cz.sima.msbank.utils

import java.text.SimpleDateFormat

/**
 * Created by Michal Šíma on 19.06.2020.
 */
object Constants {
    const val PIN_LENGTH = 4

    val API_DATE_TIME_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    val DISPLAY_DATE_TIME_FORMAT = SimpleDateFormat("dd.MM.yyyy HH:mm")
    const val DATE_FORMAT_PATTERN = "dd.MM.yyyy"
    const val MONEY_FORMAT = "###,##0.00' Kč'"
}
