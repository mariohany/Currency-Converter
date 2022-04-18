package mario.hany.currency.data.remote

import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        newRequest.url("${chain.request().url}&access_key=${BuildConfig.API_KEY}")
        return chain.proceed(newRequest.build())
    }
}