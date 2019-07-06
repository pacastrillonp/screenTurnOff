package co.pacastrillonp.turnoffscream

import co.pacastrillonp.turnoffscream.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class TurnOffScreamApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder().create(this)
}