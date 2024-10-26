package com.example.kinoteka.data.network.interceptor
import com.example.kinoteka.data.datasource.TokenDataSource
import okhttp3.Interceptor
import okhttp3.Response

class Interceptor(private val tokenDataSource: TokenDataSource) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = tokenDataSource.getToken()
        if (token?.isNotEmpty() == true) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}