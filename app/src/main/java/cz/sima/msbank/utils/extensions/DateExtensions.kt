package cz.sima.msbank.utils.extensions

import cz.sima.msbank.utils.Constants
import java.util.*

/**
 * Created by Michal Šíma on 26.06.2020.
 */
fun Date.toDisplayFormat(): String {
    return Constants.DISPLAY_DATE_TIME_FORMAT.format(this)
}
