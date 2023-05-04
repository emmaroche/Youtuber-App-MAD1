package ie.setu.youtuberApp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = ""
) : Parcelable
