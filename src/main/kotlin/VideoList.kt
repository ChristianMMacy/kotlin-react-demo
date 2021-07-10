import react.*
import react.dom.p

external interface Video {
    val id: Int
    val title: String
    val speaker: String
    val videoUrl: String
}

data class KotlinVideo(
    override val id: Int,
    override val title: String,
    override val speaker: String,
    override val videoUrl: String
) : Video

external interface VideoProps : RProps {
    var videos: List<Video>
}

@ExperimentalJsExport
class VideoList : RComponent<VideoProps, RState>() {
    override fun RBuilder.render() {
        for (video in props.videos) {
            p {
                key = video.id.toString()
                +"${video.speaker}: ${video.title}"
            }
        }
    }
}

@ExperimentalJsExport
fun RBuilder.videoList(handler: VideoProps.() -> Unit): ReactElement {
    return child(VideoList::class) {
        this.attrs(handler)
    }
}
