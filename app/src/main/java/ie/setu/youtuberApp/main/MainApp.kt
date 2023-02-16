package ie.setu.youtuberApp.main
import android.app.Application
import ie.setu.youtuberApp.models.YoutuberMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val youtubers = YoutuberMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("YouTuber started")
    }
}
