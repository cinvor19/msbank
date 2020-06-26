package cz.sima.msbank.feature.pin

import cz.sima.msbank.api.ApiService
import io.reactivex.Completable

/**
 * Created by Michal Šíma on 27.06.2020.
 */
class PinRepository(private val apiService: ApiService) {

    fun login(pin: String): Completable {
        return apiService.login(pin)
    }
}
