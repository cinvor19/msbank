package cz.sima.msbank.feature.dashboard

import android.util.Log
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
import io.reactivex.Single
import io.reactivex.functions.BiFunction

class DashboardViewModel(private val dashBoardRepository: DashBoardRepository) : BaseViewModel() {

    private val dashBoardItems: MutableLiveData<List<DashBoardItem>> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData(Loading)

    // AccountId -> loadingState
    private val transactionMap = mutableMapOf<String, TransactionLoadingState>()

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
                Log.d("Flow", "init")
                loadingState.value = Normal
                dashBoardItems.value = processDashBoardItems(it)
            },
            {
                loadingState.value = Error(it)
            })
    }

    fun processDashBoardItems(items: List<DashBoardItem>): List<DashBoardItem> {
        Log.d("Flow", "Process called")
        items.filterIsInstance<Transactionable>()
            .map {
                val loadingStatus =
                    transactionMap.getOrDefault(it.id, TransactionLoadingState.NotStarted)
                when (loadingStatus) {
                    TransactionLoadingState.NotStarted -> {
                        Log.d("Flow", "Not Started item ${it.id}")
                        it.loadingState.value = Loading
                        fetchTransactions(it.id)
                    }
                    TransactionLoadingState.Loading -> {
                        Log.d("Flow", "Loading item ${it.id}")
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
        Log.d("Flow", "Fetching $accountId")
        transactionMap[accountId] = TransactionLoadingState.Loading
        subscribe(
            Single.zip(
                dashBoardRepository.fetchDashBoard(true).firstOrError(),
                dashBoardRepository.fetchTransactionsFromApi(accountId),
                BiFunction<List<DashBoardItem>, List<Transaction>, List<DashBoardItem>> { dashBoardItems, transactions ->
                    dashBoardItems.map {
                        if (it is Transactionable) {
                            if (it.id == accountId) {
                                transactionMap[accountId] =
                                    TransactionLoadingState.Done(transactions)
                                it.loadingState.postValue(Normal)
                                it.transactions.postValue(transactions)
                            } else {
                                Log.d("Flow", "else")
                            }
                        }
                        it
                    }
                }
            ), {
                Log.d("Flow", "transa")
                dashBoardItems.value = it
            })
    }

    fun onFabButtonClick() {
        navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }

    fun onAccountClick() {
        navigate(R.id.action_navigation_dashboard_to_paymentFragment)
    }
}

sealed class TransactionLoadingState {
    object NotStarted : TransactionLoadingState()
    object Loading : TransactionLoadingState()
    data class Done(val list: List<Transaction>) : TransactionLoadingState()
}
