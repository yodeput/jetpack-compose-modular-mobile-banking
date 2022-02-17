package yodeput.mobile.banking.feature.ui.main.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import yodeput.mobile.banking.feature.ui.component.UserAvatar
import yodeput.mobile.banking.feature.ui.component.UserAvatarShimmer
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.model.User

@Composable
fun TopLayout(
    state: ViewState<User>,
    logoutPress: () -> Unit
) {
    val userData = remember { mutableStateOf(User(accountNo = "", username = "")) }
    if (state is ViewState.Success) {
        state.data.let {
            userData.value = it
        }
    }

    TopAppBar(
        backgroundColor = Color.Transparent,
        title = {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxSize().padding(top = 10.dp)
            ) {
                if (state is ViewState.Loading) UserAvatarShimmer() else UserAvatar(
                    userData.value
                )
            }
        },
        actions = {
            IconButton(
                onClick = logoutPress) {
                Icon(
                    Icons.Filled.Logout, null,
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        },
        elevation = 0.dp,
        modifier = Modifier.padding(top = 5.dp)
    )
}