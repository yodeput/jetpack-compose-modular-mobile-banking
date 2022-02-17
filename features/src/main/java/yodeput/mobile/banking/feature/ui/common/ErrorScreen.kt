package yodeput.mobile.banking.feature.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import yodeput.mobile.banking.common.theme.ComposeTheme
import yodeput.mobile.banking.feature.list.R

@Composable
fun ErrorScreen(message: String, refresh: (() -> Unit)?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            message,
            style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        Spacer(Modifier.height(16.dp))
        if (refresh != null) {
            Button(onClick = refresh) {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimary))
            }
        }
    }
}

@Composable
@Preview(name = " Light")
private fun PreviewLight() {
    ComposeTheme() {
        ErrorScreen("Error message") {}
    }
}

@Composable
@Preview(name = " Dark")
private fun PreviewDark() {
    ComposeTheme(darkTheme = true) {
        ErrorScreen("Error message") {}
    }
}