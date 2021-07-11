import npmModules.*
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.h3
import styled.css
import styled.styledButton
import styled.styledDiv

external interface VideoPlayerProps : RProps {
    var video: Video
    var onWatchVideo: (Video) -> Unit
    var isWatched: Boolean
}

@ExperimentalJsExport
val videoPlayer = functionalComponent<VideoPlayerProps> { props ->
    styledDiv {
        css {
            position = Position.absolute
            top = 10.px
            right = 10.px
        }
        h3 {
            +"${props.video.speaker}: ${props.video.title}"
        }
        styledDiv {
            css {
                display = Display.flex
                marginBottom = 10.px
            }
            emailShareButton {
                attrs.url = props.video.videoUrl
                emailIcon {
                    attrs.size = 32
                    attrs.round = true
                }
            }
            telegramShareButton {
                attrs.url = props.video.videoUrl
                telegramIcon {
                    attrs.size = 32
                    attrs.round = true
                }
            }
        }
        reactPlayer {
            attrs {
                url = props.video.videoUrl
            }
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
    }
}

@ExperimentalJsExport
fun RBuilder.videoPlayer(handler: VideoPlayerProps.() -> Unit) = child(videoPlayer) {
    attrs {
        handler()
    }
}
