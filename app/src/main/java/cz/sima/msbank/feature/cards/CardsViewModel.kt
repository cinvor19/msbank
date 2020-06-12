package cz.sima.msbank.feature.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.base.BaseViewModel

class CardsViewModel : BaseViewModel() {

    private val cartItems: MutableLiveData<List<Card>> = MutableLiveData()

    fun getCartItems(): LiveData<List<Card>> = cartItems


    fun fetchData() {
        cartItems.value = Card.getMockCards()
    }

    fun onCardItemClicked() {
        cartItems.value = Card.getMockCards().reversed()
    }
}