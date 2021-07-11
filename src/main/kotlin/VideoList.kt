import kotlinx.html.js.onClickFunction
import react.*
import react.dom.p

external interface Video {
    val id: Int
    val title: String
    val speaker: String
    val videoUrl: String
}

external interface VideoListProps : RProps {
    var videos: List<Video>
    var selected: Video?
    var onSelectVideo: (Video) -> Unit
}

@ExperimentalJsExport
val videoList = functionalComponent<VideoListProps> { props ->
    for (video in props.videos) {
        p {
            key = video.id.toString()
            attrs {
                onClickFunction = {
                    props.onSelectVideo(video)
                }
            }
            if (video == props.selected) {
                +"▶ "
            }
            +"${video.speaker}: ${video.title}"
        }
    }
}

@ExperimentalJsExport
fun RBuilder.videoList(handler: VideoListProps.() -> Unit) = child(videoList) {
    attrs {
        handler()
    }
}
