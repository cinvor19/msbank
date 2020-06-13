package cz.sima.msbank

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.sima.msbank.api.Api
import cz.sima.msbank.api.NetConfig
import cz.sima.msbank.feature.cards.CardsViewModel
import cz.sima.msbank.feature.dashboard.DashboardViewModel
import cz.sima.msbank.feature.pin.PinViewModel
import cz.sima.msbank.feature.settings.SettingsViewModel
import cz.sima.msbank.feature.splash.SplashScreenViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Michal Šíma on 06.06.2020.
 */
val viewModelModule = module {
    viewModel { SplashScreenViewModel() }
    viewModel { DashboardViewModel() }
    viewModel { CardsViewModel() }
    viewModel { SettingsViewModel() }
    viewModel { PinViewModel() }

}

val repositoryModule = module {
    // TODO repository, modules
}

val apiModule = module {
    single { provideOkHttpClient(androidContext()) }
    single { provideRetrofit(get()) }

    // API
    single { provideApi(get(), Api::class.java) }
}

private fun provideOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        })
        .addInterceptor(ChuckInterceptor(context)).build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(NetConfig.BASE_URL)
        // .addCallAdapterFactory(CoroutineCallAdapterFactory()) // TODO add when using Coroutines
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()
            )
        ).build()
}

private fun <T> provideApi(retrofit: Retrofit, cls: Class<T>): T {
    return retrofit.create(cls)
}

val allModules = listOf(viewModelModule, repositoryModule, apiModule)