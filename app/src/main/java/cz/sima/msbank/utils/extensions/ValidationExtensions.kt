package cz.sima.msbank.utils.extensions

import android.text.TextUtils
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

/**
 * Created by Michal Šíma on 28.06.2020.
 */
fun TextInputLayout.validateNotEmpty(error: String): Flowable<Boolean>? {
    return this.validateLength(1, error)
}

fun TextInputLayout.validateLength(requiredLength: Int, error: String): Flowable<Boolean>? {
    return this.editText?.let {
        it
            .textChanges()
            .skipWhile { t -> TextUtils.isEmpty(t) }
            .defaultDebounceAndSchedulers()
            .checkMinimumLength(requiredLength)
            .toFlowable(BackpressureStrategy.LATEST)
            .doOnNext { isValid -> showValidationError(isValid, this, error) }
    }
}

fun TextInputLayout.validateRule(ruleFunction: (CharSequence) -> Boolean, error: String): Flowable<Boolean>? {
    return this.editText?.let {
        it
            .textChanges()
            .skipWhile { t -> TextUtils.isEmpty(t) }
            .defaultDebounceAndSchedulers()
            .checkRule(ruleFunction)
            .toFlowable(BackpressureStrategy.LATEST)
            .doOnNext { isValid -> showValidationError(isValid, this, error) }
    }
}


fun showValidationError(isValid: Boolean?, textInput: TextInputLayout, error: String) {
    isValid?.let {
        if (it) {
            textInput.isErrorEnabled = false
        } else {
            textInput.error = error
        }
    }
}

fun Observable<CharSequence>.defaultDebounceAndSchedulers(): Observable<CharSequence> {
    return this.debounce(300, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(AndroidSchedulers.mainThread())
}

fun Observable<CharSequence>.checkMinimumLength(requiredLength: Int): Observable<Boolean> {
    return this.map { input -> input.length >= requiredLength }
}

fun Observable<CharSequence>.checkRule(ruleFunction: (CharSequence) -> Boolean): Observable<Boolean> {
    return this.map { input -> ruleFunction(input) }
}
