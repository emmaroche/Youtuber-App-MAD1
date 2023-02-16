package ie.setu.youtuberApp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class YoutuberModel(var id: Long = 0,
                         var name: String = "",
                         var channelName: String = "") : Parcelable