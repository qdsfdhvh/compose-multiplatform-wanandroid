package me.seiko.jetpack.dispatcher

fun interface BackHandler {
  fun handleBackPress(): Boolean
}
