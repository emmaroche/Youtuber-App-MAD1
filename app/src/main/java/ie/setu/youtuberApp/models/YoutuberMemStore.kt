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
            logAll()
        }
    }

    override fun delete(youtuber: YoutuberModel) {
        logAll()
        youtubers.remove(youtuber)
        logAll()
    }

//    override fun filter(youtuber: YoutuberModel) {
//
////        if (numberOfFavouriteYoutubers() == 0) i ("\n No YouTubers stored as favourites")
////        else youtubers.filter { youtubers -> youtubers.isFavouriteYoutuber }
//
//
//        youtubers.filter { youtubers -> youtubers.name}
//        logAll()
//
//    }

//    private fun numberOfFavouriteYoutubers(): Int = youtubers.count { youtubers -> youtubers.isFavouriteYoutuber }

    private fun logAll() {
        youtubers.forEach { i("$it") }
    }
}
