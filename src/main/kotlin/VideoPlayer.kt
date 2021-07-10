import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.h3
import react.dom.img
import styled.css
import styled.styledButton
import styled.styledDiv

external interface VideoPlayerProps : RProps {
    var video: Video
    var onWatchVideo: (Video) -> Unit
    var isWatched: Boolean
}

@ExperimentalJsExport
class VideoPlayer : RComponent<VideoPlayerProps, RState>() {
    override fun RBuilder.render() {
        styledDiv {
            css {
                position = Position.absolute
                top = 10.px
                right = 10.px
            }
            h3 {
                +"${props.video.speaker}: ${props.video.title}"
            }
            styledButton {
                css {
                    display = Display.block
                    backgroundColor = if (props.isWatched) Color.red else Color.lightGreen
                }
                attrs {
                    onClickFunction = {
                        props.onWatchVideo(props.video)
                    }
                }
                +"Mark as ${if(props.isWatched) "un" else ""}watched."
            }
            img {
                attrs {
                    src = "https://via.placeholder.com/640x320.png?text=Video+Player+Placeholder"
                }
            }
        }
    }
}

@ExperimentalJsExport
fun RBuilder.videoPlayer(handler: VideoPlayerProps.() -> Unit): ReactElement {
    return child(VideoPlayer::class) {
        this.attrs(handler)
    }
}
