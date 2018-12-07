package com.remipradal.starwars.tripdetail

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
abstract class TripDetailModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideService(retrofit: Retrofit): TripDetailRepositoryImpl.Service {
            return retrofit.create(TripDetailRepositoryImpl.Service::class.java)
        }

        @Provides
        @JvmStatic
        fun provideCompositeDisposable() = CompositeDisposable()

    }

    @Binds
    @Suppress("unused")
    abstract fun bindTripDetailRepository(tripDetailRepositoryImpl: TripDetailRepositoryImpl): TripDetailRepository
}