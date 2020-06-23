package cz.sima.msbank.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by Michal Šíma on 06.06.2020.
 */
interface ApiService {

    @GET("dashboard")
    fun fetchDashBoard(): Single<DashboardResponse>

}
