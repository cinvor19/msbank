package cz.sima.msbank.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import cz.sima.msbank.event.AnySingleLiveEvent
import cz.sima.msbank.event.LiveEvent
import cz.sima.msbank.event.LiveEventMap
import cz.sima.msbank.event.NavigationEvent
import cz.sima.msbank.utils.extensions.applySchedulers
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import kotlin.reflect.KClass

/**
 * Created by Michal Šíma on 06.06.2020.
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    protected val showTodoToast: AnySingleLiveEvent = AnySingleLiveEvent()

    fun getShowTodoToast(): LiveData<Any> = showTodoToast

    private val liveEventMap = LiveEventMap()

    fun <T : LiveEvent> subscribe(
        lifecycleOwner: LifecycleOwner,
        eventClass: KClass<T>,
        eventObserver: Observer<T>
    ) {
        liveEventMap.subscribe(lifecycleOwner, eventClass, eventObserver)
    }

    fun <T : LiveEvent> publish(event: T) {
        liveEventMap.publish(event)
    }

    fun <T> subscribe(
        single: Single<T>,
        success: (T) -> Unit = { logEvent("Success item in $single: $it") },
        error: (Throwable) -> Unit = { logEvent("Error in $single: ${it.message}") }
    )
            : Disposable {

        return single
            .applySchedulers()
            .doOnError { it.printStackTrace() }
            .subscribe(success, error)
    }

    fun subscribe(
        completable: Completable,
        complete: () -> Unit = { logEvent("OnComplete in $completable") },
        error: (Throwable) -> Unit = { logEvent("Error in $completable: ${it.message}") }
    )
            : Disposable {
        return completable
            .applySchedulers()
            .doOnError { it.printStackTrace() }
            .subscribe(complete, error)
    }

    fun <T> subscribe(
        observable: Observable<T>,
        next: (T) -> Unit = { logEvent("Next item in $observable: $it") },
        error: (Throwable) -> Unit = { logEvent("Error in $observable: ${it.message}") }
    )
            : Disposable {
        return observable
            .applySchedulers()
            .doOnError { it.printStackTrace() }
            .subscribe(next, error)
    }

    fun <T> subscribe(
        flowable: Flowable<T>,
        next: (T) -> Unit = { logEvent("Next item in $flowable: $it") },
        error: (Throwable) -> Unit = { logEvent("Error in $flowable: ${it.message}") }
    )
            : Disposable {
        return flowable
            .applySchedulers()
            .doOnError { it.printStackTrace() }
            .subscribe(next, error)
    }

    private fun logEvent(logMsg: String) = Log.d("BaseVM RxEvent", logMsg)

    protected fun navigate(
        @IdRes resId: Int,
        args: Bundle? = null,
        navOptions: NavOptions? = null
    ) {
        publish(NavigationEvent(resId, args, navOptions))
    }

    protected fun navigate(directions: NavDirections, navOptions: NavOptions? = null) {
        publish(NavigationEvent(directions, navOptions))
    }
}
