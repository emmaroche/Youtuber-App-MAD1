package ie.setu.youtuberApp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.setu.youtuberApp.main.MainApp
import ie.setu.youtuberApp.models.YoutuberModel
import ie.setu.youtuberApp.databinding.ActivityYoutuberBinding
import timber.log.Timber.i

class YoutuberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYoutuberBinding
    private var youtuber = YoutuberModel()
    private lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYoutuberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        i("YouTuber Activity started...")

        binding.btnAdd.setOnClickListener {
            youtuber.name = binding.youtuberName.text.toString()
            youtuber.channelName = binding.youtuberChannelName.text.toString()
            if (youtuber.name.isNotEmpty()) {

                app.youtubers.add(youtuber.copy())
                i("add Button Pressed: $youtuber")
                for (i in app.youtubers.indices) {
                    i("YouTuber[$i]:${this.app.youtubers[i]}")
                }
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a name", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}