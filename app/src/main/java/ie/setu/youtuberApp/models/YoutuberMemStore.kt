package ie.setu.youtuberApp.models

import timber.log.Timber.i

class YoutuberMemStore : YoutuberStore {

    private val youtubers = ArrayList<YoutuberModel>()

    override fun findAll(): List<YoutuberModel> {
        return youtubers
    }

    override fun create(youtuber: YoutuberModel) {
        youtubers.add(youtuber)
        logAll()
    }

    private fun logAll() {
        youtubers.forEach{ i("$it") }
    }
}
