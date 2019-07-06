package co.pacastrillonp.turnoffscream.view

import androidx.lifecycle.ViewModel
import co.pacastrillonp.turnoffscream.di.util.ViewModelKey
import co.pacastrillonp.turnoffscream.viewmodel.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class MainActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel
}