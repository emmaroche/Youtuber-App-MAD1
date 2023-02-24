package ie.setu.youtuberApp.models

interface YoutuberStore {

    fun findAll(): List<YoutuberModel>

    fun create(youtuber: YoutuberModel)

    fun update(youtuber: YoutuberModel)

    fun delete(youtuber: YoutuberModel)

}