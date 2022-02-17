package yodeput.mobile.banking.core.network

import android.content.Context
import yodeput.mobile.banking.core.database.PreferenceStorage
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val pref = PreferenceStorage(context)
        val request = originalRequest.newBuilder().url(originalRequest.url)
            .headers(getHeaders(pref.token ?: "",)).build()
        val response = chain.proceed(request)
        return response
    }

    private fun getHeaders(token: String = ""): Headers {
        val builder = Headers.Builder()
            .add("Content-Type", "application/json")
        if (token.isNotEmpty() && token.isNotBlank()) builder.add("Authorization",token)
        return builder.build()
    }
}