package cz.sima.msbank.feature.cards

import androidx.databinding.ObservableArrayList
import cz.sima.msbank.base.BaseViewModel

class CardsViewModel : BaseViewModel() {

    val items = ObservableArrayList<Card>()

    init {
        items.addAll(Card.getMockCards())
    }
}