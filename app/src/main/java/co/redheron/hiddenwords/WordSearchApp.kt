package co.redheron.hiddenwords

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WordSearchApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
