package cz.sima.msbank.feature.dashboard.model

import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.shared.Transaction

/**
 * Created by Michal Šíma on 26.06.2020.
 */
interface Transactionable {
    val id: String
    val transactions: MutableLiveData<List<Transaction>>
    val loadingState: MutableLiveData<LoadingState>
}
