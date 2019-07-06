package co.pacastrillonp.turnoffscream.di

import android.content.Context
import co.pacastrillonp.turnoffscream.TurnOffScreamApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideApplicationContext(application: TurnOffScreamApplication): Context = application.applicationContext
}