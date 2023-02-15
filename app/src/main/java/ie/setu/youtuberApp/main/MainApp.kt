package ie.setu.youtuberApp.main
import android.app.Application
import ie.setu.youtuberApp.models.YoutuberModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val youtubers = ArrayList<YoutuberModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("YouTuber started")
    }
}
