package app.midterm.twitter.data

import app.midterm.twitter.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

object UserStorage {

    val userFlow = MutableStateFlow(
        User(username = "testuser")
    )

    fun updateUserName(name: String) {
        userFlow.update {
            it.copy(username = name)
        }
    }
}

