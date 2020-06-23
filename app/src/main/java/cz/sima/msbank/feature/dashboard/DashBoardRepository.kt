package cz.sima.msbank.feature.dashboard

import cz.sima.msbank.api.ApiService
import io.reactivex.Completable

/**
 * Created by Michal Šíma on 23.06.2020.
 */
class DashBoardRepository(private val apiService: ApiService) {


    fun getAny(): Completable {
        return apiService.getAny()
    }

}
