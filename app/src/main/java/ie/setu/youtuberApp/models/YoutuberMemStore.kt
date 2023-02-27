package ie.setu.youtuberApp.models

import android.content.Context
import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class YoutuberMemStore(applicationContext: Context) : YoutuberStore {

    private val youtubers = ArrayList<YoutuberModel>()

    override fun findAll(): List<YoutuberModel> {
        return youtubers
    }

    override fun create(youtuber: YoutuberModel) {
        youtuber.id = getId()
        youtubers.add(youtuber)
        logAll()
    }

    override fun update(youtuber: YoutuberModel) {
        val foundYoutuber: YoutuberModel? = youtubers.find { p -> p.id == youtuber.id }
        if (foundYoutuber != null) {
            foundYoutuber.name = youtuber.name
            foundYoutuber.channelName = youtuber.channelName
//            foundYoutuber.youtuberRating = youtuber.youtuberRating
//            foundYoutuber.dob = youtuber.dob
            logAll()
        }
    }

    override fun delete(youtuber: YoutuberModel){
        youtubers.remove(youtuber)
    }

    private fun logAll() {
        youtubers.forEach { i("$it") }
    }
}
