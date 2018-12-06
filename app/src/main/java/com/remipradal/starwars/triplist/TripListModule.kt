package com.remipradal.starwars.triplist

import com.remipradal.starwars.BuildConfig
import com.remipradal.starwars.core.triplist.TripListPresenter
import com.remipradal.starwars.core.triplist.TripListRepository
import com.remipradal.starwars.core.triplist.TripListRepositoryImpl
import com.remipradal.starwars.core.triplist.TripListTransformer
import dagger.Binds
import dagger.Module
import dagger.Provides
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

    }

    @Binds
    abstract fun bindTripListPresenter(tripListPresenterImpl: TripListPresenterImpl): TripListPresenter

    @Binds
    abstract fun bindTripListRepository(tripListRepositoryImpl: TripListRepositoryImpl): TripListRepository

    @Binds
    abstract fun bindDisplayHolder(tripListPresenterImpl: TripListPresenterImpl): DisplayHolder<TripListDisplay>

}