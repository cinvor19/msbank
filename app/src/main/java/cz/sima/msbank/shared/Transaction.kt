package cz.sima.msbank.shared

import androidx.recyclerview.widget.DiffUtil
import cz.sima.msbank.api.TransactionResponse
import cz.sima.msbank.utils.Constants
import java.math.BigDecimal
import java.util.*

/**
 * Created by Michal Šíma on 26.06.2020.
 */
data class Transaction(
    val id: String,
    val date: Date,
    val accountId: String,
    val amount: BigDecimal,
    val counterPart: String
) {
    companion object {
        fun fromApi(apiObject: TransactionResponse, accountId: String): Transaction {
            return Transaction(
                apiObject.id,
                Constants.API_DATE_TIME_FORMAT.parse(apiObject.date),
                accountId,
                BigDecimal(apiObject.amount),
                apiObject.counterPart
            )
        }
    }
}

object TransactionDiffUtil : DiffUtil.ItemCallback<Transaction>() {

    override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return newItem.id == oldItem.id
    }

    override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
        return newItem.amount == oldItem.amount &&
                newItem.accountId == oldItem.accountId &&
                newItem.counterPart == oldItem.counterPart
    }
}
