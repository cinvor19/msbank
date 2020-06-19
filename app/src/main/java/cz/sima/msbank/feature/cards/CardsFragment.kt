package cz.sima.msbank.feature.cards

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseVMFragment
import cz.sima.msbank.databinding.FragmentCardsBinding
import kotlinx.android.synthetic.main.fragment_cards.*

class CardsFragment : BaseVMFragment<FragmentCardsBinding, CardsViewModel>(CardsViewModel::class) {

    override fun getLayoutId() = R.layout.fragment_cards

    override fun hasBottomNav() = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        viewModel.fetchData()
    }

    private fun initRecycler() {
        val adapter = CardAdapter(viewModel)
        recyclerCards.layoutManager = LinearLayoutManager(context)

        recyclerCards.adapter = adapter

        observe(viewModel.getCartItems()) {
            adapter.submitList(it)
        }
    }
}
