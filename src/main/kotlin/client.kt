import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window

@ExperimentalJsExport
fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            app {}
        }
    }
}
