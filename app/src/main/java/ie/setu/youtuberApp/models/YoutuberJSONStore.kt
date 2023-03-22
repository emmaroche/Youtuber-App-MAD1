package ie.setu.youtuberApp.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.setu.youtuberApp.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "youtubers.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<YoutuberModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class YoutuberJSONStore(private val context: Context) : YoutuberStore {

    private var youtubers = mutableListOf<YoutuberModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<YoutuberModel> {
        logAll()
        return youtubers
    }

    override fun clear(): List<YoutuberModel> {
        youtubers.clear()
        serialize()
        return youtubers
    }

    override fun create(youtuber: YoutuberModel) {
        youtuber.id = generateRandomId()
        youtubers.add(youtuber)
        serialize()
    }

    override fun update(youtuber: YoutuberModel) {
        val foundYoutuber: YoutuberModel? = youtubers.find { p -> p.id == youtuber.id }
        if (foundYoutuber != null) {
            foundYoutuber.name = youtuber.name
            foundYoutuber.channelName = youtuber.channelName
            foundYoutuber.youtuberRating = youtuber.youtuberRating
            foundYoutuber.youtuberImage = youtuber.youtuberImage
            foundYoutuber.dob = youtuber.dob
            foundYoutuber.isFavouriteYoutuber = youtuber.isFavouriteYoutuber
            foundYoutuber.lat = youtuber.lat
            foundYoutuber.lng = youtuber.lng
            foundYoutuber.zoom = youtuber.zoom
            logAll()
        }
        serialize()
    }

    override fun delete(youtuber: YoutuberModel) {
        logAll()
        youtubers.remove(youtuber)
        logAll()
        serialize()
    }

    override fun filter(youtuber: Boolean) {
        if (numberOfFavouriteYoutubers() == 0) Timber.i("\n No YouTubers stored as favourites")
        val filteredList = youtubers.filter { it.isFavouriteYoutuber }
        Timber.i("$filteredList")
        serialize()
    }

    private fun numberOfFavouriteYoutubers(): Int =
        youtubers.count { youtubers -> youtubers.isFavouriteYoutuber }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(youtubers, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        youtubers = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        youtubers.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>, JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}
