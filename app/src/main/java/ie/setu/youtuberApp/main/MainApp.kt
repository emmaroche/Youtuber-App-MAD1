package ie.setu.youtuberApp.main

import android.app.Application
import ie.setu.youtuberApp.models.YoutuberMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    var youtubers = YoutuberMemStore()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("YouTubers App Starting...")
    }

}