package cz.sima.msbank.feature.cards

import androidx.recyclerview.widget.DiffUtil
import cz.sima.msbank.utils.Validations
import java.util.*

/**
 * Created by Michal Šíma on 07.06.2020.
 */
data class Card(
    val id: String = UUID.randomUUID().toString(),
    val cardNumber: String,
    val cardType: CardType = CardType.fromCardNumber(cardNumber)
) {
    companion object {
        fun getMockCards(): List<Card> {
            return listOf(
                Card("1", "1111222233334444", CardType.MASTER_CARD),
                Card("2", "1234123412341234", CardType.VISA),
                Card("3", "1122334411223344", CardType.AMERICAN_EXPRESS)
            )
        }
    }

    fun isCardValid(): Boolean {
        return Validations.isCardNumberValid(cardNumber)
    }

}

class CardItemDiffCallback : DiffUtil.ItemCallback<Card>() {
    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.cardNumber == newItem.cardNumber
                && oldItem.cardType == newItem.cardType
    }

    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }
}

enum class CardType {
    MASTER_CARD {
        override fun isCardType(cardNumber: String): Boolean {
            return cardNumber.substring(0, 6).toInt() in 222100..272009 ||
                    cardNumber.substring(0, 2).toInt() in 51..55
        }
    },
    MAESTRO {
        override fun isCardType(cardNumber: String): Boolean {
            val prefixes =
                listOf("5018", "5020", "5038", "5893", "6304", "6759", "6761", "6762", "6763")
            prefixes.forEach {
                if (cardNumber.startsWith(it)) {
                    return true
                }
            }
            return false
        }
    },
    VISA {
        override fun isCardType(cardNumber: String): Boolean {
            return cardNumber.startsWith("4")
        }
    },
    AMERICAN_EXPRESS {
        override fun isCardType(cardNumber: String): Boolean {
            return cardNumber.substring(0, 2).contains("3[47]")
        }
    },
    DISCOVER {
        override fun isCardType(cardNumber: String): Boolean {
            val prefixes =
                listOf("6011", "644", "645", "646", "647", "648", "649", "65")
            prefixes.forEach {
                if (cardNumber.startsWith(it)) {
                    return true
                }
            }
            return cardNumber.substring(0, 6).toInt() in 622126..622925
        }
    },
    JCB {
        override fun isCardType(cardNumber: String): Boolean {
            return cardNumber.substring(0, 4).toInt() in 3528..3589
        }
    },
    INVALID,
    UNKNOWN;

    open fun isCardType(cardNumber: String): Boolean {
        return false
    }

    companion object {
        fun fromCardNumber(cardNumber: String): CardType {
            if (!Validations.isCardNumberValid(cardNumber)) {
                return INVALID
            }
            values().forEach {
                if (it.isCardType(cardNumber)) {
                    return it
                }
            }
            return UNKNOWN
        }
    }
}
