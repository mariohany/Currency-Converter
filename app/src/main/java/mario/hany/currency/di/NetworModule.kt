package mario.hany.currency.di

import android.app.Application
import androidx.room.Room
import mario.hany.currency.BuildConfig
import mario.hany.currency.data.local.RatesDatabase
import mario.hany.currency.data.remote.AuthInterceptor
import mario.hany.currency.data.remote.CurrencyApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = BuildConfig.BASE_URL

val networkModule = module {
    factory { AuthInterceptor() }
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    factory { provideOkHttpClient(get(), get()) }
    factory { provideRetrofit(get()) }
    factory { provideCurrencyApi(get()) }

    single { provideHistoryDatabase(androidApplication()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(
    httpInterceptor: HttpLoggingInterceptor,
    authInterceptor: AuthInterceptor
): OkHttpClient {
    return OkHttpClient().newBuilder()
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(httpInterceptor)
        .build()
}

fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi =
    retrofit.create(CurrencyApi::class.java)

fun provideHistoryDatabase(app: Application): RatesDatabase =
    Room.databaseBuilder(
        app,
        RatesDatabase::class.java,
        "ratesHistory.db"
    ).build()
