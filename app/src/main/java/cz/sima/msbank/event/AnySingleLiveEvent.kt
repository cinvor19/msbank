package cz.sima.msbank.event

/**
 * Created by Michal Šíma on 14.06.2020.
 */
open class AnySingleLiveEvent : SingleLiveEvent<Any>() {

    fun publish() {
        value = Any()
    }

    fun publishOnBackground() {
        postValue(Any())
    }
}