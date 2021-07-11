@file:JsModule("react-youtube-lite")
@file:JsNonModule

package npmModules

import react.RClass
import react.RProps

@JsName("ReactYouTubeLite")
external val reactPlayer: RClass<ReactYouTubeProps>

external interface ReactYouTubeProps: RProps {
    var url: String
}
