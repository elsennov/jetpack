package com.elsen.jetpack.base.data.server

import com.elsen.jetpack.BuildConfig
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.applicationContext
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

/**
 * Created by elsennovraditya on 23/06/18
 */
object RetrofitModule {

    val module = applicationContext {
        bean("server_retrofit") {
            Retrofit.Builder()
                .client(get())
                .baseUrl(get<String>("base_url"))
                .addCallAdapterFactory(get())
                .addConverterFactory(get("any_on_empty_converter"))
                .addConverterFactory(get("gson_converter"))
                .build()
        }

        bean {
            OkHttpClient.Builder()
                .addInterceptor(get("header_interceptor"))
                .addInterceptor(get("http_logging_interceptor"))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        }

        bean("header_interceptor") {
            Interceptor { chain ->
                val request = chain.request()
                val headerInterceptedRequest = request.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(request.method(), request.body())
                    .build()

                chain.proceed(headerInterceptedRequest)
            }
        }

        bean("http_logging_interceptor") {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            httpLoggingInterceptor as Interceptor
        }

        bean("base_url") {
            BuildConfig.BASE_URL
        }

        bean {
            RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()) as CallAdapter.Factory
        }

        bean("gson_converter") {
            GsonConverterFactory.create(get("lower_case_with_underscores_gson")) as Converter.Factory
        }

        bean("lower_case_with_underscores_gson") {
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        }

        bean("any_on_empty_converter") {
            object : Converter.Factory() {
                override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, Any> {
                    val delegate: Converter<ResponseBody, Any> = retrofit.nextResponseBodyConverter(this, type, annotations)
                    return Converter { body ->
                        if (body.contentLength() == 0L) return@Converter null
                        delegate.convert(body)
                    }
                }
            } as Converter.Factory
        }
    }

}