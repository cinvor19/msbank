package cz.sima.msbank.feature.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.sima.msbank.R
import cz.sima.msbank.base.BaseViewModel
import cz.sima.msbank.customview.loadingview.Error
import cz.sima.msbank.customview.loadingview.Loading
import cz.sima.msbank.customview.loadingview.LoadingState
import cz.sima.msbank.customview.loadingview.Normal
import cz.sima.msbank.feature.dashboard.model.DashBoardItem
import cz.sima.msbank.feature.dashboard.model.Transactionable
import cz.sima.msbank.shared.Transaction

class DashboardViewModel(private val dashBoardRepository: DashBoardRepository) : BaseViewModel() {

    private val dashBoardItems: MutableLiveData<List<DashBoardItem>> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData(Loading)
    private val notifyItemChange: MutableLiveData<Int> = MutableLiveData()

    // AccountId -> loadingState
    private val transactionMap = mutableMapOf<String, TransactionLoadingState>()

    fun getDashBoardItems(): LiveData<List<DashBoardItem>> = dashBoardItems
    fun getLoadingState(): LiveData<LoadingState> = loadingState
    fun getNotifyItemChange(): LiveData<Int> = notifyItemChange

    init {
        fetchDashboard()
    }

    fun fetchDashboard() {
        subscribe(dashBoardRepository.fetchDashBoard().doOnSubscribe {
            loadingState.postValue(Loading)
        },
            {
                loadingState.value = Normal
                dashBoardItems.value = processDashBoardItems(it)
            },
            {
                loadingState.value = Error(it)
            })
    }

    private fun processDashBoardItems(items: List<DashBoardItem>): List<DashBoardItem> {
        items.filterIsInstance<Transactionable>()
            .map {
                val loadingStatus =
                    transactionMap.getOrDefault(it.id, TransactionLoadingState.NotStarted)
                when (loadingStatus) {
                    TransactionLoadingState.NotStarted -> {
                        it.loadingState.value = Loading
                        fetchTransactions(it.id)
                    }
                    TransactionLoadingState.Loading -> {
                        it.loadingState.value = Loading
                    }
                    is TransactionLoadingState.Done -> {
                        it.loadingState.value = Normal
                        it.transactions.value = loadingStatus.list
                    }
                }
            }
        return items
    }

    private fun fetchTransactions(accountId: String) {
        transactionMap[accountId] = TransactionLoadingState.Loading
        subscribe(dashBoardRepository.fetchTransactionsFromApi(accountId), { transactions ->
            dashBoardItems.value
                ?.filterIsInstance<Transactionable>()
                ?.filter { it.id == accountId }
                ?.map { item ->
                    transactionMap[accountId] = TransactionLoadingState.Done(transactions)
                    item.loadingState.value = Normal
                    item.transactions.value = transactions
                    notifyItemChange.value = dashBoardItems.value?.indexOf(item as DashBoardItem)
                }
        })
    }

    fun onFabButtonClick() {
        navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }

    fun onAccountClick() {
        showTodoToast.publish()
    }

    fun onAccountPayClick() {
        showTodoToast.publish()
    }

    fun onAccountQrClick() {
        showTodoToast.publish()
    }

    fun onAccountInfoClick() {
        showTodoToast.publish()
    }

    fun onAccountSettingsClick() {
        showTodoToast.publish()
    }

    fun onAccountRecyclerClick() {
        showTodoToast.publish()
    }
}

sealed class TransactionLoadingState {
    object NotStarted : TransactionLoadingState()
    object Loading : TransactionLoadingState()
    data class Done(val list: List<Transaction>) : TransactionLoadingState()
}
