package app.midterm.twitter.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.midterm.twitter.data.MessagesStorage
import app.midterm.twitter.model.Message
import androidx.compose.material3.TextField
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDetailsScreen(
    index: Int,
    message: Message,
    navController: NavController
) {
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Message"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Message(message = message)
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { MessagesStorage.makeFavoriteMessage(index) }
        ) {
            Image(
                painter = if (message.isFavorite.value) {
                    painterResource(id = android.R.drawable.btn_star_big_on)
                } else {
                    painterResource(id = android.R.drawable.btn_star_big_off)
                },
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigateUp()
                MessagesStorage.deleteMessage(index)
            }
        ) {
            Text(text = "Delete")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                isDialogVisible = true
            }
        ) {
            Text(text = "Update")
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
                Text(text = "Update message")
                var newMessage by remember {
                    mutableStateOf(message.message)
                }
                TextField(
                    value = newMessage,
                    onValueChange = { newMessage = it }
                )
                Button(
                    onClick = {
                        MessagesStorage.updateMessage(index, newMessage)
                        isDialogVisible = false
                    }
                ) {
                    Text(text = "Apply")
                }
            }
        }
    }
}
