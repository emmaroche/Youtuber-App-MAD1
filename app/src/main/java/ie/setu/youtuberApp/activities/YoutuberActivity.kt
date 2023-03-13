package ie.setu.youtuberApp.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.setu.youtuberApp.R
import ie.setu.youtuberApp.databinding.ActivityYoutuberBinding
import ie.setu.youtuberApp.helpers.showImagePicker
import ie.setu.youtuberApp.main.MainApp
import ie.setu.youtuberApp.models.Location
import ie.setu.youtuberApp.models.YoutuberModel
import timber.log.Timber.i
import java.util.*

class YoutuberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYoutuberBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private var youtuber = YoutuberModel()
    private lateinit var app: MainApp
    private var edit = false
    private var datePickerDialog: DatePickerDialog? = null
    private var dateButton: Button? = null
    private var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityYoutuberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Calendar/date picker
        initDatePicker()
        dateButton = findViewById(R.id.datePickerButton)
        dateButton?.text = getTodaysDate()

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        i("YouTuber Activity started...")

        if (intent.hasExtra("youtuber_edit")) {
            edit = true
            youtuber = intent.extras?.getParcelable("youtuber_edit")!!
            binding.youtuberName.setText(youtuber.name)
            binding.youtuberChannelName.setText(youtuber.channelName)
            binding.youtuberRating.value = youtuber.youtuberRating
            binding.datePickerButton.text = youtuber.dob
            binding.btnAdd.setText(R.string.button_saveYoutuber)
            Picasso.get()
                .load(youtuber.youtuberImage)
                .resize(900, 800)
                .into(binding.youtuberImage)
        }

        binding.btnAdd.setOnClickListener {
            youtuber.name = binding.youtuberName.text.toString()
            youtuber.channelName = binding.youtuberChannelName.text.toString()
            youtuber.youtuberRating = binding.youtuberRating.value
            youtuber.dob = binding.datePickerButton.text.toString()

            if (youtuber.name.isEmpty()) {
                Snackbar.make(it, R.string.error_Text, Snackbar.LENGTH_LONG)
                    .show()

            } else {
                if (edit) {
                    app.youtubers.update(youtuber.copy())
                    i("Edit Button Pressed: $youtuber")
                    setResult(RESULT_OK)
                    finish()
                } else {
                    app.youtubers.create(youtuber.copy())
                    i("Add Button Pressed: $youtuber")
                    setResult(RESULT_OK)
                    finish()
                }
            }

        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        binding.youtuberLocation.setOnClickListener {
            if (youtuber.zoom != 0f) {
                location.lat =  youtuber.lat
                location.lng = youtuber.lng
                location.zoom = youtuber.zoom
            }
            val launcherIntent = Intent(this, MapsActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }
        registerImagePickerCallback()
        setUpNumberPicker()
        registerMapCallback()

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add_youtuber, menu)
        if (edit) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {

                // Code resource used to help: https://www.javatpoint.com/kotlin-android-alertdialog
                val builder = AlertDialog.Builder(this)
                //set title for alert dialog
                builder.setTitle(R.string.dialogTitle)
                //set message for alert dialog
                builder.setMessage(R.string.dialogMessage)
                builder.setIcon(R.drawable.baseline_warning)

                //yes option selected
                builder.setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(applicationContext, "YouTuber Deleted", Toast.LENGTH_LONG).show()
                    i("Delete Button Pressed: $youtuber")
                    app.youtubers.delete(youtuber)
                    i("After delete Button Pressed: ${app.youtubers.findAll()}")
                    setResult(RESULT_OK)
                    finish()
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
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpNumberPicker() {
        val numberPicker = binding.youtuberRating
        numberPicker.minValue = 1
        numberPicker.maxValue = 10
        numberPicker.wrapSelectorWheel = false
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            youtuber.youtuberImage = result.data!!.data!!
                            Picasso.get()
                                .load(youtuber.youtuberImage)
                                .resize(900, 800)
                                .into(binding.youtuberImage)
                        }
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

    private fun getTodaysDate(): String {
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        var month = cal[Calendar.MONTH]
        month += 1
        val day = cal[Calendar.DAY_OF_MONTH]
        return makeDateString(day, month, year)
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { _, year, month, day ->
                var month = month
                month += 1
                val date = makeDateString(day, month, year)
                dateButton?.text = date
            }
        val cal = Calendar.getInstance()
        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]
        datePickerDialog = DatePickerDialog(this, dateSetListener, year, month, day)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) return "JAN"
        if (month == 2) return "FEB"
        if (month == 3) return "MAR"
        if (month == 4) return "APR"
        if (month == 5) return "MAY"
        if (month == 6) return "JUN"
        if (month == 7) return "JUL"
        if (month == 8) return "AUG"
        if (month == 9) return "SEP"
        if (month == 10) return "OCT"
        if (month == 11) return "NOV"
        return if (month == 12) "DEC" else "JAN"
    }

    fun openDatePicker(view: View?) {
        datePickerDialog?.show()
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                            youtuber.lat = location.lat
                            youtuber.lng = location.lng
                            youtuber.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}




