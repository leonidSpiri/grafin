package ru.spiridonov.grafin.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.spiridonov.grafin.GrafinApp
import ru.spiridonov.grafin.presentation.*

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: GrafinApp)
    fun inject(activity: MainActivity)
    fun inject(fragment: GameFinishedFragment)
    fun inject(fragment: GameFragment)
    fun inject(fragment: WelcomeFragment)
    fun inject(fragment: ChooseLevelFragment)
    fun inject(fragment: AwardsFragment)
    fun inject(fragment: LoyaltyFragment)
    fun inject(fragment: TheoryFragment)
    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}