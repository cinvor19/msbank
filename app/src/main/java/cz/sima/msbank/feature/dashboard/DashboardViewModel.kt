package cz.sima.msbank.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.customview.loadingview.Error
import cz.sima.msbank.customview.loadingview.Loading
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.customview.loadingview.Normal
import cz.sima.msbank.feature.dashboard.model.DashBoardAccount
import cz.sima.msbank.feature.dashboard.model.DashBoardItem

class DashboardViewModel(private val dashBoardRepository: DashBoardRepository) : BaseViewModel() {

    private val dashBoardItems: MutableLiveData<List<DashBoardItem>> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData(Loading)

    fun getDashBoardItems(): LiveData<List<DashBoardItem>> = dashBoardItems
    fun getLoadingState(): LiveData<LoadingState> = loadingState

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        subscribe(dashBoardRepository.fetchDashBoard()
            .doOnSubscribe {
                loadingState.postValue(Loading)
            },
            {
                loadingState.value = Normal
                dashBoardItems.value = it
                processDashBoardItems(it)
            },
            {
                loadingState.value = Error(it)
            })
    }

    fun processDashBoardItems(items: List<DashBoardItem>) {
        items.filterIsInstance<DashBoardAccount>()
            .forEach {
                initTransactionFlowable(it.id)
                requestTransactions(it.id)
            }
    }

    private fun initTransactionFlowable(accountId: String) {
        subscribe(dashBoardRepository.fetchTransactionsFromDb(accountId))
    }

    private fun requestTransactions(accountId: String) {
        dashBoardRepository.requestTransactionsFromApi(accountId)
    }

    fun onFabButtonClick() {
        navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }

    fun onAccountClick() {
        navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }
}
