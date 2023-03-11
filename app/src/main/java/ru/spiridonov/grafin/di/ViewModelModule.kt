package ru.spiridonov.grafin.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.spiridonov.grafin.presentation.viewmodels.MainViewModel

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindAccountViewModel(viewModel: MainViewModel): ViewModel
}