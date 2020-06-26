package cz.sima.msbank.utils.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import cz.sima.msbank.R
import cz.sima.msbank.utils.Constants
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

/**
 * Created by Michal Šíma on 26.06.2020.
 */
@BindingAdapter(value = ["money", "withColors"], requireAll = false)
fun TextView.setText(money: BigDecimal, withColors: Boolean = false) {
    val formatSymbols = DecimalFormatSymbols(Locale.getDefault())
    formatSymbols.decimalSeparator = ','
    formatSymbols.groupingSeparator = ' '
    val df = DecimalFormat(Constants.MONEY_FORMAT, formatSymbols)
    text = df.format(money)

    if (withColors) {
        when (money.compareTo(BigDecimal(BigInteger.ZERO))) {
            1 -> setTextColor(this.context.getColor(R.color.textColorMoneyPositive))
            -1 -> setTextColor(this.context.getColor(R.color.textColorMoneyNegative))
        }
    }
}
