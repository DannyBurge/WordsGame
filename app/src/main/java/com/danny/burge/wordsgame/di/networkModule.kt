package com.danny.burge.wordsgame.di


import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.danny.burge.wordsgame.BuildConfig
import com.danny.burge.wordsgame.constants.WIKIPEDIA_URL
import com.danny.burge.wordsgame.interaction.WikipediaApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L
private const val CONNECT_TIMEOUT = 30L

private const val QUALIFIER_RETROFIT = "retrofit_weather"
private const val QUALIFIER_NETWORK_CLIENT = "weather_http_client"

val networkModule = module {
    // HTTP Clients
    single(named(QUALIFIER_NETWORK_CLIENT)) { provideOkHttpClient(androidContext(), get()) }

    // Retrofit
    single(named(QUALIFIER_RETROFIT)) {
        provideRetrofit(
            client = get(named(QUALIFIER_NETWORK_CLIENT)),
            url = WIKIPEDIA_URL
        )
    }
    single { get<Retrofit>(named(QUALIFIER_RETROFIT)).create(WikipediaApi::class.java) }

    // Stuff
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.HEADERS
            }
        }
    }
}

private fun provideOkHttpClient(
    context: Context,
    loggingInterceptor: HttpLoggingInterceptor
): OkHttpClient = OkHttpClient.Builder().apply {
    connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
    readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        addInterceptor(loggingInterceptor)
    }
    addInterceptor(
        ChuckerInterceptor.Builder(context)
            .collector(ChuckerCollector(context))
            .maxContentLength(250000L)
            .redactHeaders(emptySet())
            .alwaysReadResponseBody(false)
            .build()
    )
    if (BuildConfig.DEBUG) {
        ignoreAllSSLErrors()
    }
}.build()

fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
    val naiveTrustManager = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
    }

    val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
        val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
        init(null, trustAllCerts, SecureRandom())
    }.socketFactory

    sslSocketFactory(insecureSocketFactory, naiveTrustManager)
    hostnameVerifier { _, _ -> true }
    return this
}

private fun provideRetrofit(client: OkHttpClient, url: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(createMoshiConverterFactory())
        .build()
}

private fun createMoshiConverterFactory(): MoshiConverterFactory {
    val moshi = Moshi.Builder()
        .build()
    return MoshiConverterFactory.create(moshi).asLenient()
}