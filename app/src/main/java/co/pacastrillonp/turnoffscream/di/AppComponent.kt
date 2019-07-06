package co.pacastrillonp.turnoffscream.di

import co.pacastrillonp.turnoffscream.TurnOffScreamApplication
import co.pacastrillonp.turnoffscream.di.util.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ViewModelModule::class,
        ActivityBuilder::class]
)

interface AppComponent : AndroidInjector<TurnOffScreamApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TurnOffScreamApplication>()
}