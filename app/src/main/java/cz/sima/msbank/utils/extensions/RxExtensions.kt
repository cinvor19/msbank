package cz.sima.msbank.utils.extensions

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/*
 *  Copyright (c) 2018 adastra.one a.s., all rights reserved.
 */

fun <T> Single<T>.applySchedulers(): Single<T> {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}

fun <T> Maybe<T>.applySchedulers(): Maybe<T> {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}

fun <T> Flowable<T>.applySchedulers(): Flowable<T> {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}

fun Completable.applySchedulers(): Completable {
    return this
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
}

@SuppressLint("CheckResult")
fun <T> Single<T>.fireAndForget() {
    this
            .subscribeOn(Schedulers.io())
            .subscribe(
                    {
                        Log.d(this.javaClass.simpleName, "Fire and forget $this on success: $it")
                    },
                    {
                        Log.d(this.javaClass.simpleName, "Fire and forget $this on error: ${it.stackTrace}")
                    })
}

@SuppressLint("CheckResult")
fun Completable.fireAndForget() {
    this
            .subscribeOn(Schedulers.io())
            .subscribe(
                    {
                        Log.d(this.javaClass.simpleName, "Fire and forget $this on complete")
                    },
                    {
                        Log.d(this.javaClass.simpleName, "Fire and forget $this on error: ${it.stackTrace}")
                    })
}
