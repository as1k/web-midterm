package app.midterm.twitter.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import app.midterm.twitter.data.MessagesStorage
import app.midterm.twitter.data.UserStorage
import app.midterm.twitter.model.Message
import app.midterm.twitter.ui.Screen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Content(
    navController: NavHostController
) {
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Feed"
                )
                val messages by MessagesStorage.messages.collectAsState()
                LazyColumn {
                    itemsIndexed(messages) { index, message ->
                        Message(
                            message = message,
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        navController.navigate(
                                            Screen.Feed.tag + "/" + index
                                        )
                                    }
                                )
                        )
                    }
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 16.dp),
                onClick = { isDialogVisible = true }) {
                Text(text = "Add Message")
            }
        }
    }
    if (isDialogVisible) {
        Dialog(
            onDismissRequest = { isDialogVisible = false }
        ) {
            val shape = RoundedCornerShape(16.dp)
            Column(
                modifier = Modifier
                    .border(
                        color = Color.DarkGray,
                        width = 0.5.dp,
                        shape = shape
                    )
                    .clip(shape)
                    .background(Color.White)
                    .padding(32.dp)
            ) {
                Text(text = "Write message...")
                var newMessage by remember {
                    mutableStateOf("")
                }
                TextField(
                    value = newMessage,
                    onValueChange = { newMessage = it }
                )
                Button(
                    onClick = {
                        MessagesStorage.addMessage(
                            message = newMessage
                        )
                        isDialogVisible = false
                    }
                ) {
                    Text(text = "Message")
                }
            }
        }
    }
}

@Composable
fun Message(
    message: Message,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    Column(
        modifier = modifier
            .border(
                color = Color.DarkGray,
                width = 0.5.dp,
                shape = shape
            )
            .clip(shape)
            .background(Color.White)
            .padding(32.dp)
    ) {
        Text(text = "Message: ${message.message}")
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Uploaded Date: ${message.uploadedDate}")
        Spacer(modifier = Modifier.size(8.dp))
        Text(text = "Uploaded by ${UserStorage.userFlow.collectAsState().value.username}")
    }
}
