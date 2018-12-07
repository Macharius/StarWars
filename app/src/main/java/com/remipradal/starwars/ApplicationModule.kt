package com.remipradal.starwars

import com.remipradal.starwars.common.TripListTransformer
import com.remipradal.starwars.utils.DateTimeMoshiAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Provides
    @JvmStatic
    fun provideTripListTransformer(): TripListTransformer {
        return TripListTransformer(BuildConfig.BASE_URL)
    }

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
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    @JvmStatic
    @Named("WORKER")
    fun provideWorkerScheduler(): Scheduler = Schedulers.io()

    @Provides
    @JvmStatic
    @Named("UI")
    fun provideUiScheduler(): Scheduler = AndroidSchedulers.mainThread()

}