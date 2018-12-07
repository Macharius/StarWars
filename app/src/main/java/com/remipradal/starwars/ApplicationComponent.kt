package com.remipradal.starwars

import com.remipradal.starwars.tripdetail.TripDetailActivity
import com.remipradal.starwars.tripdetail.TripDetailModule
import com.remipradal.starwars.triplist.TripListActivity
import com.remipradal.starwars.triplist.TripListModule
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivitiesInjectorModule::class
])

interface ApplicationComponent : AndroidInjector<StarWarsTripApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<StarWarsTripApplication>()
}

@Module
abstract class ActivitiesInjectorModule {

    @ContributesAndroidInjector(modules = [TripListModule::class])
    @Suppress("unused")
    abstract fun contributeTripListActivityInjector(): TripListActivity

    @ContributesAndroidInjector(modules = [TripDetailModule::class])
    @Suppress("unused")
    abstract fun contributeTripDetailActivityInjector(): TripDetailActivity

}

