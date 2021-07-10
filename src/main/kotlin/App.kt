import kotlinx.css.*
import react.*
import react.dom.*

external interface AppState : RState {
    var selectedVideo: Video?
    var unWatchedVideos: List<Video>
    var watchedVideos: List<Video>
}

@ExperimentalJsExport
class App : RComponent<RProps, AppState>() {

    /**
     * Update the selected video
     */
    private val handleSelectVideo = { video: Video ->
        setState {
            selectedVideo = video
        }
    }

    /**
     * Handle marking a video as watched or unwatched
     */
    private val handleWatchVideo = { video: Video ->
        setState {
            if(video in unWatchedVideos) {
                setState {
                    unWatchedVideos = unWatchedVideos - video
                    watchedVideos = watchedVideos + video
                }
            } else {
                setState {
                    watchedVideos = watchedVideos - video
                    unWatchedVideos = unWatchedVideos + video
                }
            }
        }
    }

    override fun AppState.init() {
        unWatchedVideos = listOf(
            KotlinVideo(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
            KotlinVideo(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
            KotlinVideo(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
        )

        watchedVideos = listOf(
            KotlinVideo(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
        )
    }

    override fun RBuilder.render() {
        h1 {
            +"KotlinConf Explorer"
        }
        div {
            h3 {
                +"Videos to watch"
            }
            videoList {
                videos = state.unWatchedVideos
                selectedVideo = state.selectedVideo
                onSelectVideo = handleSelectVideo
            }
            h3 {
                +"Videos watched"
            }
            videoList {
                videos = state.watchedVideos
                selectedVideo = state.selectedVideo
                onSelectVideo = handleSelectVideo
            }
        }
        state.selectedVideo?.let { selectedVideo ->
            videoPlayer {
                video = selectedVideo
                onWatchVideo = handleWatchVideo
                isWatched = state.watchedVideos.contains(selectedVideo)
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
