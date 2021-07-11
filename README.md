# Writing Modern React with Kotlin
This app is a demo that was produced using the ["Building Web Applications with React and Kotlin/JS"](https://play.kotlinlang.org/hands-on/Building%20Web%20Applications%20with%20React%20and%20Kotlin%20JS/10_Addendum_Modern_React_with_Hooks) walkthrough from KotlinConf.

The app demonstrates using Kotlin/JS to build a React application, complete with Webpack bundling. One of the major benefits of this approach is that you get all the type safety and language constructs that Kotlin has to offer, while also getting the client-side efficiency and tooling that React offers.

To run the app, run the "run" Gradle task. This will build and serve the current code.
To run the app with live reloading, add the "--continuous" flag. This will cause a refresh when code updates.

E.g. `./gradlew run --continuous`

To build the app for deployment, run `./gradlew build`. The distribution artifacts will be placed in `build/distributions`.

# Items of note
The app demonstrates some interesting features, like using NPM modules, using hooks (though commits prior to this demonstrate class-based components instead), and using both regular and styled elements.

For full details, see the walkthough. Otherwise, you may find it valuable to step through the commits to watch the progression of the app development.
