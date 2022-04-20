package mario.hany.currency.data.remote

import mario.hany.currency.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
        val apiKey = if(chain.request().url.toString().contains("?", true))
            "&access_key=${BuildConfig.API_KEY}"
        else
            "?access_key=${BuildConfig.API_KEY}"
        newRequest.url("${chain.request().url}$apiKey")
        return chain.proceed(newRequest.build())
    }
}