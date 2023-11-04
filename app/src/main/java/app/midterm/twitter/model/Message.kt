package app.midterm.twitter.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class Message(
    val username: String,
    val uploadedDate: String,
    val message: String,
    val isFavorite: MutableState<Boolean> = mutableStateOf(false)
)