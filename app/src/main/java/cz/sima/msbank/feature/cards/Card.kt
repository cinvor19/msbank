package cz.sima.msbank.feature.cards

/**
 * Created by Michal Šíma on 07.06.2020.
 */
data class Card(
    val id: String,
    val number: String,
    val cardType: CardType
) {
    companion object {
        fun getMockCards(): ArrayList<Card> {
            return arrayListOf(
                Card("1", "1111222233334444", CardType.MASTER_CARD),
                Card("2", "1234123412341234", CardType.VISA),
                Card("3", "1122334411223344", CardType.AMERICAN_EXPRESS)
            )
        }
    }
}

enum class CardType {
    MASTER_CARD,
    VISA,
    AMERICAN_EXPRESS
}