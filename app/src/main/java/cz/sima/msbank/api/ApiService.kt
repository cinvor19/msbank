package cz.sima.msbank.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Michal Šíma on 06.06.2020.
 */
interface ApiService {

    @GET("dashboard")
    fun fetchDashBoard(): Single<DashboardResponse>

    @GET("transaction/{accountId}")
    fun fetchTransactions(@Path("accountId") accountId: String): Single<List<TransactionResponse>>

    @GET("login/{pin}")
    fun login(@Path("pin") pin: String): Completable
}
