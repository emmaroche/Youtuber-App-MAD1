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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import ie.setu.youtuberApp.R
import ie.setu.youtuberApp.databinding.ActivityYoutuberBinding
import ie.setu.youtuberApp.helpers.showImagePicker
import ie.setu.youtuberApp.main.MainApp
import ie.setu.youtuberApp.models.Location
import ie.setu.youtuberApp.models.YoutuberModel
import timber.log.Timber.i
import java.util.*


class YoutuberView : AppCompatActivity() {

    private lateinit var binding: ActivityYoutuberBinding
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private var youtuber = YoutuberModel()
    private lateinit var app: MainApp
    private var edit = false
    private var datePickerDialog: DatePickerDialog? = null
    private var dateButton: Button? = null
//    private var location = Location(52.245696, -7.139102, 15f)

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
            binding.youtuberChannelName.setText(youtuber.channelName)
            binding.youtuberName.setText(youtuber.name)
            binding.youtuberRating.value = youtuber.youtuberRating
            binding.datePickerButton.text = youtuber.dob
            binding.btnAdd.setText(R.string.button_saveYoutuber)
            Picasso.get()
                .load(youtuber.youtuberImage)
                .resize(900, 800)
                .into(binding.youtuberImage)
        }

        val db = FirebaseFirestore.getInstance()
        val youtuberRef = db.collection("youtubers")
        youtuberRef.whereEqualTo("id", youtuber.id.toString()).get()


        binding.btnAdd.setOnClickListener {
            youtuber.channelName = binding.youtuberChannelName.text.toString()
            youtuber.name = binding.youtuberName.text.toString()
            youtuber.youtuberRating = binding.youtuberRating.value
            youtuber.dob = binding.datePickerButton.text.toString()

            // Code resources used to store data in Firebase: https://www.youtube.com/watch?v=5UEdyUFi_uQ and https://ansarali-edugaon.medium.com/how-to-add-data-on-firebase-firestore-in-kotlin-android-fe114070d550

            val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

            // Create a map of the YouTuber's data
            val data: MutableMap<String, Any> = HashMap()
            data["name"] = youtuber.name
            data["channelName"] = youtuber.channelName
            data["rating"] = youtuber.youtuberRating
            data["dob"] = youtuber.dob
            data["userId"] = userId

            if (youtuber.channelName.isEmpty()) {
                Snackbar.make(it, R.string.error_Text, Snackbar.LENGTH_LONG).show()
            } else {
                if (edit) {
                    // Update existing YouTuber in the App and Firestore
                    youtuberRef.document(youtuber.id.toString())
                        .set(data)
                        .addOnSuccessListener {
                            app.youtubers.update(youtuber.copy())
                            i("Edit Button Pressed: $youtuber")
                            setResult(RESULT_OK)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                applicationContext,
                                "Error updating document: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                } else {
                    // Add  YouTuber to the App and Firestore
                    youtuberRef.add(data)
                        .addOnSuccessListener {
                            app.youtubers.create(youtuber.copy())
                            i("Add Button Pressed: $youtuber")
                            setResult(RESULT_OK)
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                applicationContext,
                                "Error adding document: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                }
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher, this)
        }

        binding.youtuberLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (youtuber.zoom != 0f) {
                location.lat = youtuber.lat
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

                // Code resource used to help with delete all: https://www.javatpoint.com/kotlin-android-alertdialog
                val builder = AlertDialog.Builder(this)
                // Set title for alert dialog
                builder.setTitle(R.string.dialogTitle)
                // Set message for alert dialog
                builder.setMessage(R.string.dialogMessage)
                builder.setIcon(R.drawable.baseline_warning)

                // Yes option selected
                builder.setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(applicationContext, "YouTuber Deleted", Toast.LENGTH_LONG).show()
                    i("Delete Button Pressed: $youtuber")
                    app.youtubers.delete(youtuber)
                    i("After delete Button Pressed: ${app.youtubers.findAll()}")
                    setResult(RESULT_OK)
                    finish()
                }

                // Cancel option selected
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")

                            val image = result.data!!.data!!
                            contentResolver.takePersistableUriPermission(
                                image,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                            youtuber.youtuberImage = image

                            Picasso.get()
                                .load(youtuber.youtuberImage)
                                .into(binding.youtuberImage)
                            binding.chooseImage.setText(R.string.change_youtuber_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }


    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            youtuber.lat = location.lat
                            youtuber.lng = location.lng
                            youtuber.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> {}
                    else -> {}
                }
            }
    }

}




