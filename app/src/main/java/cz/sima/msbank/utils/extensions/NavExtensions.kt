package cz.sima.msbank.utils.extensions

import androidx.navigation.NavController
import cz.sima.msbank.event.NavigationEvent

/**
 * Created by Michal Šíma on 06.06.2020.
 */
fun NavController.navigate(navEvent: NavigationEvent) {
    navEvent.resId?.let {
        navigate(it, navEvent.navArgs, navEvent.navOptions)
        return
    }
    navEvent.navDirections?.let {
        navigate(it, navEvent.navOptions)
        return
    }
}
