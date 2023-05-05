package ie.setu.youtuberApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.setu.youtuberApp.R
import ie.setu.youtuberApp.databinding.CardYoutuberBinding
import ie.setu.youtuberApp.models.YoutuberModel

interface YoutuberListener {
    fun onYoutuberClick(youtuber: YoutuberModel)
    fun onButtonClick(youtuber: YoutuberModel)
}

class YoutuberAdapter constructor(
    private var youtubers: List<YoutuberModel>,
    private val listener: YoutuberListener
) :
    RecyclerView.Adapter<YoutuberAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardYoutuberBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val youtuber = youtubers[holder.adapterPosition]
        holder.bind(youtuber, listener)

        // Sets the state of the toggle button based on the YoutuberModel
        holder.binding.chooseFav.isChecked = youtuber.isFavouriteYoutuber
    }

    override fun getItemCount(): Int = youtubers.size

    class MainHolder(val binding: CardYoutuberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(youtuber: YoutuberModel, listener: YoutuberListener) {
            binding.youtuberChannelName.text = youtuber.channelName
            binding.youtuberName.text = youtuber.name
            binding.youtuberRating.text = youtuber.youtuberRating.toString()
            binding.youtuberDobText.text = youtuber.dob
            Picasso.get()
                .load(youtuber.youtuberImage)
                .placeholder(R.mipmap.person_red_icon_foreground)
                .error(R.mipmap.person_red_icon_foreground)
                .into(binding.displayImage)
            binding.root.setOnClickListener { listener.onYoutuberClick(youtuber) }
            binding.chooseFav.setOnClickListener { listener.onButtonClick(youtuber) }
        }
    }
}

