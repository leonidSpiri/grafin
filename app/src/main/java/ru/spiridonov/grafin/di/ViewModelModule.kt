package ru.spiridonov.grafin.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.spiridonov.grafin.presentation.viewmodels.AwardsViewModel
import ru.spiridonov.grafin.presentation.viewmodels.MainViewModel

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(AwardsViewModel::class)
    fun bindAwardsViewModel(viewModel: AwardsViewModel): ViewModel
}