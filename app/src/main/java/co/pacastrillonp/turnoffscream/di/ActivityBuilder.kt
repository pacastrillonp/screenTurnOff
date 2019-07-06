package co.pacastrillonp.turnoffscream.di

import co.pacastrillonp.turnoffscream.di.util.ActivityScoped
import co.pacastrillonp.turnoffscream.view.MainActivity
import co.pacastrillonp.turnoffscream.view.MainActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ActivityScoped
    @ContributesAndroidInjector (modules = [MainActivityModule::class])
    internal abstract fun MainActivity(): MainActivity
}