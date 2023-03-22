package ie.setu.youtuberApp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import ie.setu.youtuberApp.R

//Code resource to help create splash screen: https://www.geeksforgeeks.org/how-to-create-a-splash-screen-in-android-using-kotlin/
// & also https://rex50.medium.com/amazing-animated-splash-screen-kotlin-and-lottie-how-to-b98504005abf
@SuppressLint("CustomSplashScreen")
@Suppress("DEPRECATION")
class SplashScreenActivity : AppCompatActivity() {

    companion object {

        const val ANIMATION_TIME: Long = 2500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

// Hide the status bar and make the splash screen full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(this.mainLooper).postDelayed({

            startActivity(Intent(this, YouTuberListActivity::class.java))

            finish()

        }, ANIMATION_TIME)

    }

}

