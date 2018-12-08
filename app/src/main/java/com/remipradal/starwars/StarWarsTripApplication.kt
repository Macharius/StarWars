package com.remipradal.starwars

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class StarWarsTripApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().create(this)
    }

}