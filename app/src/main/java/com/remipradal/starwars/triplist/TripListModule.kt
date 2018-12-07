package com.remipradal.starwars.triplist

import com.remipradal.starwars.BuildConfig
import com.remipradal.starwars.common.TripListTransformer
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit

@Module
abstract class TripListModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun provideTripListTransformer(): TripListTransformer {
            return TripListTransformer(BuildConfig.BASE_URL)
        }

        @Provides
        @JvmStatic
        fun provideService(retrofit: Retrofit): TripListRepositoryImpl.Service {
            return retrofit.create(TripListRepositoryImpl.Service::class.java)
        }

        @Provides
        @JvmStatic
        fun provideCompositeDisposable() = CompositeDisposable()

    }

    @Binds
    @Suppress("unused")
    abstract fun bindTripListRepository(tripListRepositoryImpl: TripListRepositoryImpl): TripListRepository

}