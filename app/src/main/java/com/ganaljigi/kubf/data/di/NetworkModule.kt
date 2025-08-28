package com.ganaljigi.kubf.data.di

import com.ganalijigi.kubf.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.net.InetAddress
import javax.inject.Singleton
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.dnsoverhttps.DnsOverHttps

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val STUB_BASE_URL = "https://example.invalid/"

    @Provides
    @Singleton
    fun providesJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        encodeDefaults = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

//    @Provides
//    @Singleton
//    fun providesOkHttpClient(
//        loggingInterceptor: HttpLoggingInterceptor,
//    ): OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(loggingInterceptor)
//        .build()

    @Provides
    @Singleton
    fun providesOkHttpClient(
        logging: HttpLoggingInterceptor,
    ): OkHttpClient {
        val doh = DnsOverHttps.Builder().client(OkHttpClient())
            .url("https://cloudflare-dns.com/dns-query".toHttpUrl())
            .bootstrapDnsHosts(
                InetAddress.getByName("1.1.1.1"),
                InetAddress.getByName("1.0.0.1"),
                InetAddress.getByName("2606:4700:4700::1111"),
                InetAddress.getByName("2606:4700:4700::1001"),
            )
            .build()

        return OkHttpClient.Builder()
            .dns(doh) // ← 핵심
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        client: OkHttpClient,
        json: Json,
    ): Retrofit{
        val raw = kotlin.runCatching { BuildConfig.BASE_URL }
            .getOrNull().orEmpty().trim()

        val normalized = when{
            raw.isBlank() -> STUB_BASE_URL
            "://".let { !raw.contains(it) } -> "httpsL//$raw/"
            else -> if(raw.endsWith("/")) raw else "$raw/"
        }
        return Retrofit.Builder()
            .baseUrl(normalized)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
//    ): Retrofit = Retrofit.Builder()
//        .baseUrl(BuildConfig.BASE_URL)
//        .client(client)
//        .addConverterFactory(
//            json.asConverterFactory("application/json".toMediaType())
//        )
//        .build()

}