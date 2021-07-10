import kotlinx.css.*
import react.*
import react.dom.*
import styled.css
import styled.styledDiv

val unwatchedVideos = listOf(
    KotlinVideo(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
    KotlinVideo(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
    KotlinVideo(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
)

val watchedVideos = listOf(
    KotlinVideo(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
)

@ExperimentalJsExport
class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        h1 {
            +"KotlinConf Explorer"
        }
        div {
            h3 {
                +"Videos to watch"
            }
            videoList{
                videos = unwatchedVideos
            }
            h3 {
                +"Videos watched"
            }
            videoList{
                videos = watchedVideos
            }
        }
        styledDiv {
            css {
                position = Position.absolute
                top = 10.px
                right = 10.px
            }
            h3 {
                +"John Doe: Building and breaking things"
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
fun RBuilder.app(handler: RProps.() -> Unit): ReactElement {
    return child(App::class) {
        this.attrs(handler)
    }
}
