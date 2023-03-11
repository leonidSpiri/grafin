package ru.spiridonov.grafin

import android.app.Application
import android.content.Context
import ru.spiridonov.grafin.di.DaggerApplicationComponent

class GrafinApp:Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
        appContext = applicationContext
    }

    companion object{
        lateinit var appContext: Context
    }

}