package ie.setu.youtuberApp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.setu.youtuberApp.databinding.CardYoutuberBinding
import ie.setu.youtuberApp.models.YoutuberModel


class YoutuberAdapter constructor(private var youtubers: List<YoutuberModel>) :
    RecyclerView.Adapter<YoutuberAdapter.MainHolder>() {

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
