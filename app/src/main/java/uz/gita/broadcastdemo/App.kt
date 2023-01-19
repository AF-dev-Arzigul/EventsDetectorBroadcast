package uz.gita.broadcastdemo

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
        Repository.init()
    }
}