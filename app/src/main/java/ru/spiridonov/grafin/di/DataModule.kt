package ru.spiridonov.grafin.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.spiridonov.grafin.data.network.ApiFactory
import ru.spiridonov.grafin.data.network.ApiService
import ru.spiridonov.grafin.data.repository.GameRepositoryImpl
import ru.spiridonov.grafin.domain.repository.GameRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindWorkListRepository(impl: GameRepositoryImpl): GameRepository

    companion object {
        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}