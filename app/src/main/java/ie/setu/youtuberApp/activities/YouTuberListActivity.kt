package ie.setu.youtuberApp.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ie.setu.youtuberApp.R
import ie.setu.youtuberApp.adapters.YoutuberAdapter
import ie.setu.youtuberApp.adapters.YoutuberListener
import ie.setu.youtuberApp.databinding.ActivityYouTuberListBinding
import ie.setu.youtuberApp.main.MainApp
import ie.setu.youtuberApp.models.YoutuberModel
import timber.log.Timber

class YouTuberListActivity : AppCompatActivity(), YoutuberListener {

    private lateinit var app: MainApp
    private lateinit var binding: ActivityYouTuberListBinding
    private var youtuber = YoutuberModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYouTuberListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = YoutuberAdapter(app.youtubers.findAll(), this)

    }

    //layout and populate for display

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, YoutuberActivity::class.java)
                getResult.launch(launcherIntent)
            }

            R.id.item_filter -> {
                Timber.i("Filter Button Pressed")
                app.youtubers.filter(youtuber.isFavouriteYoutuber)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {

                (binding.recyclerView.adapter)?.notifyItemRangeChanged(
                    0,
                    app.youtubers.findAll().size
                )
            }
        }

    override fun onYoutuberClick(youtuber: YoutuberModel) {
        val launcherIntent = Intent(this, YoutuberActivity::class.java)
        launcherIntent.putExtra("youtuber_edit", youtuber)
        getClickResult.launch(launcherIntent)
    }

    override fun onButtonClick(youtuber: YoutuberModel) {
        Timber.i("Before Favourite Button Pressed, isFavouriteYouTuber = ${youtuber.isFavouriteYoutuber}")

        findViewById<View>(R.id.chooseFav) as ToggleButton

        youtuber.isFavouriteYoutuber = !youtuber.isFavouriteYoutuber

        Timber.i("After Favourite Button Pressed, isFavouriteYouTuber = ${youtuber.isFavouriteYoutuber}")
    }

    @SuppressLint("NotifyDataSetChanged")
    private val getClickResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                //Log messages to check if youtuber item is updated and/or deleted if delete or update buttons are clicked
                Timber.i("Get Clicked Button Pressed ${app.youtubers.findAll()}")

                (binding.recyclerView.adapter)?.notifyDataSetChanged()

//              (binding.recyclerView.adapter)?.
//              notifyItemRangeChanged(0,app.youtubers.findAll().size)
            }
        }

}
