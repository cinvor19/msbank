package cz.sima.msbank.cards

import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentCardsBinding

class CardsFragment : BaseVMFragment<FragmentCardsBinding, CardsViewModel>(CardsViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_cards

    override fun hasBottomNav() = true

}
