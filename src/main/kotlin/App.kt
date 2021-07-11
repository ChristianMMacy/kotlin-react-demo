import kotlinx.browser.window
import kotlinx.coroutines.*
import react.*
import react.dom.*

/**
 * Get a single video's descriptor from the API
 * @param id The ID of the video descriptor to fetch
 * @return The fetched video descriptor
 */
suspend fun fetchVideo(id: Int): Video {
    val url = "https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id"
    val response = window
        .fetch(url)
        .await()
        .json()
        .await()
    return response as Video
}

/**
 * Fetches a list of video descriptors
 * @return A list of video descriptors
 */
suspend fun fetchVideos(): List<Video> = coroutineScope {
    (1..25).map { id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}

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
        unWatchedVideos = listOf()
        watchedVideos = listOf()

        val mainScope = MainScope()
        mainScope.launch {
            val videos = fetchVideos()
            setState {
                unWatchedVideos = videos
            }
        }
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
