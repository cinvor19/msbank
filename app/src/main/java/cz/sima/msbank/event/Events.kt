package cz.sima.msbank.event

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

/**
 * Created by Michal Šíma on 06.06.2020.
 */
class NavigationEvent() : LiveEvent() {

    var resId: Int? = null
    var navArgs: Bundle? = null
    var navDirections: NavDirections? = null
    var navOptions: NavOptions? = null

    constructor(resId: Int, navArgs: Bundle? = null, navOptions: NavOptions? = null) : this() {
        this.resId = resId
        this.navArgs = navArgs
        this.navOptions = navOptions
    }

    constructor(navDirections: NavDirections, navOptions: NavOptions? = null) : this() {
        this.navDirections = navDirections
        this.navOptions = navOptions
    }
}

class BackEvent : LiveEvent()
