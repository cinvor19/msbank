package cz.sima.msbank.shared

import cz.sima.msbank.api.TransactionResponse

/**
 * Created by Michal Šíma on 26.06.2020.
 */
data class Transaction(
    val id: String,
    val accountId: String,
    val amount: String,
    val counterPart: String
) {
    companion object {
        fun fromApi(apiObject: TransactionResponse, accountId: String): Transaction {
            return Transaction(apiObject.id, accountId, apiObject.amount, apiObject.counterPart)
        }
    }
}
