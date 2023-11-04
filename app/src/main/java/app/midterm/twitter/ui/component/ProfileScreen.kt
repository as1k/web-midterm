package app.midterm.twitter.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import app.midterm.twitter.data.UserStorage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val profile = UserStorage.userFlow.collectAsState()
    var isDialogVisible by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Profile"
        )
        Text(
            modifier = Modifier
                .padding(24.dp)
                .clickable {
                    isDialogVisible = true
                },
            text = profile.value.username
        )
        Spacer(modifier = Modifier.height(20.dp))
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
                Text(text = "change user name")
                var newName by remember {
                    mutableStateOf(profile.value.username)
                }
                TextField(
                    value = newName,
                    onValueChange = { newName = it }
                )
                Button(
                    onClick = {
                        UserStorage.updateUserName(newName)
                        isDialogVisible = false
                    }
                ) {
                    Text(text = "Apply")
                }
            }
        }
    }
}
