package ie.setu.youtuberApp.models

import android.os.Parcelable
import android.widget.DatePicker
import kotlinx.parcelize.Parcelize

@Parcelize
data class YoutuberModel(var id: Long = 0,
                         var name: String = "",
                         var channelName: String = "",
                         var youtuberRating: Int = 0,
//                         var dob: Int
                         ) : Parcelable