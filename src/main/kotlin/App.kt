import kotlinx.browser.window
import kotlinx.coroutines.*
import kotlinx.css.h3
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

@ExperimentalJsExport
val app = functionalComponent<RProps> { _ ->
    val (selectedVideo, setSelectedVideo) = useState<Video?>(null)
    val (unWatchedVideos, setUnWatchedVideos) = useState<List<Video>>(listOf())
    val (watchedVideos, setWatchedVideos) = useState<List<Video>>(listOf())

    // Once, when the component is first loaded, fetch the list of videos
    // The useEffect hook changes when any of the items in the dependency list (only parameter) change
    // Passing no parameter causes useEffect to be executed on every render--this means infinite loops if state is changing
    // Passing an emptyList() ensures that useEffect is only called once, on first render
    // If there was a possibility that "setUnWatchedVideos" could change, then it could be passed as a parameter like `listOf(setUnWatchedVideos)`
    useEffect(emptyList()) {
        val mainScope = MainScope()
        mainScope.launch {
            val videos = fetchVideos()
            setUnWatchedVideos(videos)
        }
    }

    /**
     * Handle marking a video as watched or unwatched
     */
    val handleWatchVideo = { video: Video ->
        if (video in unWatchedVideos) {
            setUnWatchedVideos(unWatchedVideos - video)
            setWatchedVideos(watchedVideos + video)
        } else {
            setWatchedVideos(watchedVideos - video)
            setUnWatchedVideos(unWatchedVideos + video)
        }
    }

    // BEGIN VIEW
    h1 {
        +"KotlinConf Explorer"
    }
    div {
        h3 {
            +"Videos to watch"
        }
        videoList {
            videos = unWatchedVideos
            selected = selectedVideo
            onSelectVideo = setSelectedVideo
        }
        h3 {
            +"Videos watched"
        }
        videoList {
            videos = watchedVideos
            selected = selectedVideo
            onSelectVideo = setSelectedVideo
        }
    }
    selectedVideo?.let { sVideo ->
        videoPlayer {
            video = sVideo
            onWatchVideo = handleWatchVideo
            isWatched = watchedVideos.contains(sVideo)
        }
    }
}

/**
 * Add a wrapper for the component so that invoking it is simpler and cleaner
 * This removes the `child(<component>)` and `attrs` block boilerplate
 */
@ExperimentalJsExport
fun RBuilder.app(handler: RProps.() -> Unit) = child(app) {
    attrs {
        handler()
    }
}
