package cz.sima.msbank.customview.loadingview

import java.io.Serializable

/**
 * Created by Michal Šíma on 14.06.2020.
 */
sealed class LoadingState : Serializable

object Normal : LoadingState()
object Loading : LoadingState()
object Empty : LoadingState()

data class Error(val throwable: Throwable) : LoadingState()

enum class InitialState {
    Normal,
    Loading,
    Empty
}