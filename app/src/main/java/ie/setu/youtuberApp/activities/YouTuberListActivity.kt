package ie.setu.youtuberApp.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.youtuberApp.R
import ie.setu.youtuberApp.main.MainApp
import ie.setu.youtuberApp.models.YoutuberModel
import ie.setu.youtuberApp.databinding.ActivityYouTuberListBinding
import ie.setu.youtuberApp.databinding.CardYoutuberBinding

class YouTuberListActivity : AppCompatActivity() {

    private lateinit var app: MainApp
    private lateinit var binding: ActivityYouTuberListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYouTuberListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = YouTuberAdapter(app.youtubers)
    }

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
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.youtubers.size)
            }
        }
}

class YouTuberAdapter constructor(private var youtubers: List<YoutuberModel>) :
    RecyclerView.Adapter<YouTuberAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardYoutuberBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val youtuber = youtubers[holder.adapterPosition]
        holder.bind(youtuber)
    }

    override fun getItemCount(): Int = youtubers.size

    class MainHolder(private val binding : CardYoutuberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(youtuber: YoutuberModel) {
            binding.youtuberName.text = youtuber.name
            binding.youtuberChannelName.text = youtuber.channelName
        }
    }
}
