package ie.setu.youtuberApp.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class YoutuberMemStore : YoutuberStore {

    private val youtubers = ArrayList<YoutuberModel>()

    override fun findAll(): List<YoutuberModel> {
        return youtubers
    }

    override fun clear(): List<YoutuberModel> {
        youtubers.clear()
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
            foundYoutuber.youtuberRating = youtuber.youtuberRating
            foundYoutuber.youtuberImage = youtuber.youtuberImage
            foundYoutuber.dob = youtuber.dob
            foundYoutuber.isFavouriteYoutuber = youtuber.isFavouriteYoutuber
            foundYoutuber.lat = youtuber.lat
            foundYoutuber.lng = youtuber.lng
            foundYoutuber.zoom = youtuber.zoom
            logAll()
        }
    }

    override fun delete(youtuber: YoutuberModel) {
        logAll()
        youtubers.remove(youtuber)
        logAll()
    }

    override fun filter(youtuber: Boolean) {

        if (numberOfFavouriteYoutubers() == 0) i("\n No YouTubers stored as favourites")
        val filteredList = youtubers.filter { it.isFavouriteYoutuber }
        i("$filteredList")

    }

    private fun numberOfFavouriteYoutubers(): Int =
        youtubers.count { youtubers -> youtubers.isFavouriteYoutuber }


    private fun logAll() {
        youtubers.forEach { i("$it") }
    }
}


