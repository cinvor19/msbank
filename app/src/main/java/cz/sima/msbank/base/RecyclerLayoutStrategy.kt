package cz.sima.msbank.base

interface RecyclerLayoutStrategy {
    fun getLayoutId(item: Any): Int
}