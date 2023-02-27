package ie.setu.youtuberApp.main
import android.app.Application
import ie.setu.youtuberApp.models.YoutuberMemStore
import ie.setu.youtuberApp.models.YoutuberStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var youtubers: YoutuberStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        youtubers = YoutuberMemStore(applicationContext)
        i("YouTubers App Starting...")
    }

}