package ru.spiridonov.grafin.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.spiridonov.grafin.GrafinApp

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: GrafinApp)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): ApplicationComponent
    }
}