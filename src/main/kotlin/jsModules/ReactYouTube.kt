@file:JsModule("react-youtube-lite")
@file:JsNonModule

package jsModules

import react.RClass
import react.RProps

@JsName("ReactYouTubeLite")
external val reactPlayer: RClass<ReactYouTubeProps>

external interface ReactYouTubeProps: RProps {
    var url: String
}
