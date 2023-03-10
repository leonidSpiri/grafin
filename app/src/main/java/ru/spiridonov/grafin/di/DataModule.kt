package ru.spiridonov.grafin.di

import dagger.Binds
import dagger.Module
import ru.spiridonov.grafin.data.repository.GameRepositoryImpl
import ru.spiridonov.grafin.domain.repository.GameRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindWorkListRepository(impl: GameRepositoryImpl): GameRepository
}