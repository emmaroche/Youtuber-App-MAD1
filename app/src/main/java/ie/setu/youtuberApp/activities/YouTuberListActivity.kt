package ie.setu.youtuberApp.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
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

    private lateinit var youtuberRV: RecyclerView
    private lateinit var filterYoutubers: YoutuberAdapter
    private lateinit var youtuberList: ArrayList<YoutuberModel>
    private lateinit var searchView: SearchView

    private val mapIntentLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )    { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYouTuberListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        youtuberRV = findViewById(R.id.recyclerView)

        youtuberList = ArrayList()

        // initialising adapter for filtering
        filterYoutubers = YoutuberAdapter(youtuberList, this)

        // setting adapter to recycler view.
        youtuberRV.adapter = filterYoutubers

        filterYoutubers.notifyDataSetChanged()

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = YoutuberAdapter(app.youtubers.findAll(), this)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        //A resource shared to me after assignment 1 was used to help me add searching functionality
        //Code has been significantly modified to work within the YouTuber app
        //resource: https://www.geeksforgeeks.org/android-searchview-with-recyclerview-using-kotlin/

        val inflater = menuInflater

        inflater.inflate(R.menu.menu_main, menu)

        val searchItem: MenuItem = menu.findItem(R.id.search_filter)

        searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = ArrayList<YoutuberModel>()
                filteredList.clear()
                app.youtubers.findAll().forEach {
                    if (it.name.contains(newText.orEmpty(), true)) {
                        filteredList.add(it)
                    }
                }
                binding.recyclerView.adapter = YoutuberAdapter(filteredList, this@YouTuberListActivity)
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, YoutuberView::class.java)
                getResult.launch(launcherIntent)
            }

            R.id.item_delete2 -> {
                // Code resource used to help: https://www.javatpoint.com/kotlin-android-alertdialog
                val builder = AlertDialog.Builder(this)
                //set title for alert dialog
                builder.setTitle(R.string.dialogTitleDeleteAll)
                //set message for alert dialog
                builder.setMessage(R.string.dialogMessageDeleteAll)
                builder.setIcon(R.drawable.baseline_warning)

                //yes option selected
                builder.setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(applicationContext, "All YouTubers Deleted", Toast.LENGTH_LONG)
                        .show()
                    Timber.i("Delete Button Pressed: $youtuber")
                    app.youtubers.clear()
                    Timber.i("After delete Button Pressed: ${app.youtubers.findAll()}")
                    val launcherIntent = Intent(this, YouTuberListActivity::class.java)
                    getResult.launch(launcherIntent)
                }

                //cancel option selected
                builder.setNegativeButton("Cancel") { _, _ ->
                    Toast.makeText(applicationContext, "Delete Cancelled", Toast.LENGTH_LONG).show()
                }

                // Create the AlertDialog
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()
            }

            R.id.item_map -> {
                val launcherIntent = Intent(this, YoutuberMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }

            R.id.item_logout -> {

                FirebaseAuth.getInstance().signOut();

                val launcherIntent = Intent(this, SignInActivity::class.java)
                getResult.launch(launcherIntent)
            }

//            R.id.item_filter -> {
//                Timber.i("Filter Button Pressed")
//                app.youtubers.filter(youtuber.isFavouriteYoutuber)
//            }
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
        val launcherIntent = Intent(this, YoutuberView::class.java)
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


