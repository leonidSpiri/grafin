package ru.spiridonov.grafin.domain.usecases

import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.repository.GameRepository

class GetGameSettingsUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(level: Level) = repository.getGameSettings(level)
}