package app.midterm.twitter.data

import app.midterm.twitter.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.OffsetDateTime

object MessagesStorage {
    val messages = MutableStateFlow(listOf<Message>())

    fun addMessage(message: String) {
        messages.update { list ->
            list.toMutableList().apply {
                add(
                    Message(
                        username = "testuser",
                        uploadedDate = OffsetDateTime.now().toString(),
                        message = message
                    )
                )
            }
        }
    }

    fun deleteMessage(index: Int) {
        messages.update { list ->
            list.toMutableList().apply {
                removeAt(index)
            }
        }
    }

    fun updateMessage(index: Int, message: String) {
        messages.update { list ->
            val newList = list.toMutableList()
            newList[index] = newList[index].copy(
                message = message
            )
            newList
        }
    }

    fun makeFavoriteMessage(
        index: Int
    ) {
        val list = messages.value
        list[index].isFavorite.value = !list[index].isFavorite.value
    }
}
