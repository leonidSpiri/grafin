package ru.spiridonov.grafin.domain.usecases

import ru.spiridonov.grafin.domain.entity.Level
import ru.spiridonov.grafin.domain.repository.GameRepository
import javax.inject.Inject

class GetGameLevelUseCase @Inject constructor(
    private val repository: GameRepository
) {
    operator fun invoke(id: Int) = repository.getGameLevel(id)
}