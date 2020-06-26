package cz.sima.msbank.feature.dashboard

import cz.sima.msbank.api.ApiService
import cz.sima.msbank.database.MsBankDao
import cz.sima.msbank.database.TransactionDb
import cz.sima.msbank.feature.dashboard.model.DashBoardAccount
import cz.sima.msbank.feature.dashboard.model.DashBoardAnnouncement
import cz.sima.msbank.feature.dashboard.model.DashBoardCreditCard
import cz.sima.msbank.feature.dashboard.model.DashBoardItem
import cz.sima.msbank.feature.dashboard.model.DashBoardItemType
import cz.sima.msbank.feature.dashboard.model.DashBoardPromo
import cz.sima.msbank.utils.extensions.fireAndForget
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.Function4

/**
 * Created by Michal Šíma on 23.06.2020.
 */
class DashBoardRepository(
    private val apiService: ApiService,
    private val msBankDao: MsBankDao
) {
    fun fetchDashBoard(): Flowable<List<DashBoardItem>> {
        return fetchDashBoardFromDb()
            .doOnSubscribe {
                fetchDashBoardApi().fireAndForget()
            }
    }

    private fun fetchDashBoardApi(): Single<List<DashBoardItem>> {
        return apiService.fetchDashBoard()
            .map { response ->
                var order = 0
                val li = mutableListOf<DashBoardItem>()
                li.addAll(response.promos.map { DashBoardPromo.fromApi(it, order++) })
                li.addAll(response.creditCards.map { DashBoardCreditCard.fromApi(it, order++) })
                li.addAll(response.accounts.map { DashBoardAccount.fromApi(it, order++) })
                li.addAll(response.announcements.map { DashBoardAnnouncement.fromApi(it, order++) })
                li.toList()
            }
            .doOnSuccess {
                insertDashBoardToDb(it)
            }
    }

    private fun insertDashBoardToDb(list: List<DashBoardItem>) {
        val completables = mutableListOf<Completable>()

        list
            .groupBy { it.getItemType() }
            .forEach {
                completables.add(
                    when (it.key) {
                        DashBoardItemType.ACCOUNT -> msBankDao.insertDashBoardAccount(it.value.map {
                            (it as DashBoardAccount).toDb()
                        })
                        DashBoardItemType.CREDIT -> msBankDao.insertDashBoardCreditCard(it.value.map {
                            (it as DashBoardCreditCard).toDb()
                        })
                        DashBoardItemType.PROMO -> msBankDao.insertDashBoardPromo(it.value.map {
                            (it as DashBoardPromo).toDb()
                        })
                        DashBoardItemType.ANNOUNCEMENT -> msBankDao.insertDashBoardAnnouncement(it.value.map {
                            (it as DashBoardAnnouncement).toDb()
                        })
                    }
                )
            }
        Completable.concat(completables).fireAndForget()
    }

    fun requestTransactionsFromApi(accountId: String) {
        apiService.fetchTransactions(accountId)
            .flattenAsObservable { it }
            .map { TransactionDb.fromApiResponse(it, accountId) }
            .toList()
            .flatMapCompletable {
                msBankDao.insertTransactions(it)
            }
            .fireAndForget()
    }

    fun fetchTransactionsFromDb(accountId: String): Flowable<List<TransactionDb>> {
        return msBankDao.getTransactions(accountId)
    }

    private fun fetchDashBoardFromDb(): Flowable<List<DashBoardItem>> {
        val promoFlow = msBankDao.fetchDashBoardPromo()
            .map {
                it.map {
                    DashBoardPromo.fromDb(it)
                }
            }

        val creditFlow = msBankDao.fetchDashBoardCreditCard()
            .map {
                it.map {
                    DashBoardCreditCard.fromDb(it)
                }
            }

        val accountFlow = msBankDao.fetchDashBoardAccount()
            .map {
                it.map {
                    DashBoardAccount.fromDb(it)
                }
            }
        val announcementFlow = msBankDao.fetchDashBoardAnnouncement()
            .map {
                it.map {
                    DashBoardAnnouncement.fromDb(it)
                }
            }

        return Flowable.zip(
            promoFlow,
            announcementFlow,
            creditFlow,
            accountFlow,
            Function4 { promos, announcements, creditCards, accounts ->
                val list = mutableListOf<DashBoardItem>()
                list.addAll(promos)
                list.addAll(announcements)
                list.addAll(creditCards)
                list.addAll(accounts)
                list.apply {
                    sortBy { it.order }
                }
            })
    }
}
