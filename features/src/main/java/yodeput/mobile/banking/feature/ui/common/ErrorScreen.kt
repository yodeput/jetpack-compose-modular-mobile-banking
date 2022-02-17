package yodeput.mobile.banking.feature.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import yodeput.mobile.banking.feature.list.R

@Composable
fun ErrorScreen(message: String, refresh: (() -> Unit)?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message)
        Spacer(Modifier.height(16.dp))
        if (refresh != null) {
            Button(onClick = refresh) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}