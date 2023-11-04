package app.midterm.twitter.ui

sealed class Screen(val tag: String) {
    object Feed : Screen("feed")
    object Profile : Screen("profile")
}
