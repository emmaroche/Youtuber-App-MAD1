package ie.setu.youtuberApp.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class YoutuberModel(var id: Long = 0,
                         var name: String = "",
                         var channelName: String = "",
                         var youtuberRating: Int = 0,
                         var youtuberImage: Uri = Uri.EMPTY,
                         var dob: String = ""
) : Parcelable