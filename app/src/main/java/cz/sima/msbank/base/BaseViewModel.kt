package cz.sima.msbank.base

import android.os.Bundle
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
