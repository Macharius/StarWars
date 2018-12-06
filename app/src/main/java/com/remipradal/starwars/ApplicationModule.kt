package com.remipradal.starwars

import android.app.Application
import com.remipradal.starwars.utils.DateTimeMoshiAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class ApplicationModule {

    @Module
    companion object {

        @Singleton
        @Provides
        @JvmStatic
        fun provideMoshi(dateTimeMoshiAdapter: DateTimeMoshiAdapter): Moshi {
            return Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .add(dateTimeMoshiAdapter)
                    .build()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder().build()
        }

        @Provides
        @JvmStatic
        @Named("UI")
        fun provideUiCoroutineScope() = CoroutineScope(Dispatchers.Main)

        @Provides
        @JvmStatic
        @Named("WORKER")
        fun provideWorkerCoroutineScope() = CoroutineScope(Dispatchers.Default)
    }


    @Binds
    abstract fun bindApplication(application: StarWarsTripApplication): Application

}