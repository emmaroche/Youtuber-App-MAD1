package ie.setu.youtuberApp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.setu.youtuberApp.R
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

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("YouTuber Activity started...")

        binding.btnAdd.setOnClickListener {
            youtuber.name = binding.youtuberName.text.toString()
            youtuber.channelName = binding.youtuberChannelName.text.toString()
            if (youtuber.name.isNotEmpty()) {
                app.youtubers.create(youtuber.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar.make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_youtuber, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}
