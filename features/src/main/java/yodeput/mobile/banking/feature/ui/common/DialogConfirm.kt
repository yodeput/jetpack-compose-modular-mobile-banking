package yodeput.mobile.banking.feature.ui.component


import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import yodeput.mobile.banking.common.theme.ComposeTheme

@Composable
fun DialogConfirm(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = {  Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground) },
        text = { Text(text = message, color = MaterialTheme.colorScheme.onBackground) },
        containerColor = MaterialTheme.colorScheme.background,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Confirm")
            }
        },
        onDismissRequest = onDismiss,
        properties = DialogProperties(
             dismissOnBackPress = false,
            dismissOnClickOutside = false,
        )
    )
}

@Composable
@Preview(name = " Light")
private fun PreviewLight() {
    ComposeTheme() {
        DialogConfirm("Title","Dialog Message", {}){}
    }
}
@Composable
@Preview(name = " Dark")
private fun PreviewDark() {
    ComposeTheme(darkTheme = true) {
        DialogConfirm("Title","Dialog Message", {}){}
    }
}