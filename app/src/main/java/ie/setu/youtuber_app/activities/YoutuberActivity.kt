package ie.setu.youtuber_app.activities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.youtuber_app.databinding.ActivityYoutuberBinding
import ie.setu.youtuber_app.models.YoutuberModel
import timber.log.Timber
import timber.log.Timber.i

class YoutuberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYoutuberBinding
    var youtuber = YoutuberModel()
    val youtubers = ArrayList<YoutuberModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYoutuberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())

        i("YouTuber Activity started...")

        binding.btnAdd.setOnClickListener() {
            youtuber.name = binding.youtuberName.text.toString()
            youtuber.channelName = binding.youtuberChannelName.text.toString()
            if (youtuber.name.isNotEmpty() && youtuber.channelName.isNotEmpty()) {
                i("add Button Pressed: $youtuber.name")
                i("add Button Pressed: $youtuber.channelName")
                youtubers.add(youtuber.copy())
                i("YouTubers $youtubers")
            }
            else {
                Snackbar
                    .make(it,"Please Enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }


    }
}