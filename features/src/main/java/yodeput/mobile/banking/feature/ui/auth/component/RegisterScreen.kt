package yodeput.mobile.banking.feature.ui.auth.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import yodeput.mobile.banking.common.theme.ComposeTheme
import yodeput.mobile.banking.feature.list.R
import yodeput.mobile.banking.feature.ui.auth.LoginFragmentDirections

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    clearForm: String,
    onLoginPress: () -> Unit,
    onRegisterClick: (username: String, password: String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val nameErrorState = remember { mutableStateOf(false) }
    val passwordErrorState = remember { mutableStateOf(false) }
    val confirmPasswordErrorState = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    when (clearForm) {
        "CLEAR" -> {
            username.value = ""
            password.value = ""
            confirmPassword.value = ""
        }
        else -> {}
    }
    val bg = if (isSystemInDarkTheme()) R.drawable.bg_only_dark else R.drawable.bg_only_light
    val logo = if (isSystemInDarkTheme()) R.drawable.logo_light else R.drawable.logo_app
    Box( modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = bg),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Image(
            modifier = Modifier.padding(start = 20.dp, top = 20.dp),
            painter = painterResource(id = logo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(color = MaterialTheme.colorScheme.background)
                .align(
                    Alignment.BottomCenter
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {


            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.ExtraBold
                    )
                ) {
                    append("R")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("egistration")
                }
            }, fontSize = 30.sp)
            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                value = username.value,
                singleLine = true,
                onValueChange = {
                    if (nameErrorState.value) {
                        nameErrorState.value = false
                    }
                    username.value = it
                },

                modifier = Modifier.fillMaxWidth(),
                isError = nameErrorState.value,
                label = {
                    Text(text = "Username", color = MaterialTheme.colorScheme.onBackground)
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    errorBorderColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )
            if (nameErrorState.value) {
                Text(text = stringResource(id = R.string.msg_is_required).replace("##", "Username"), color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.size(16.dp))

            val passwordVisibility = remember { mutableStateOf(true) }
            OutlinedTextField(
                value = password.value,
                singleLine = true,
                onValueChange = {
                    if (passwordErrorState.value) {
                        passwordErrorState.value = false
                    }
                    password.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Password", color = MaterialTheme.colorScheme.onBackground)
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    errorBorderColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        passwordVisibility.value = !passwordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "visibility",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (passwordErrorState.value) {
                Text(text = stringResource(id = R.string.msg_is_required).replace("##", "Password"), color = MaterialTheme.colorScheme.primary)
            }

            Spacer(Modifier.size(16.dp))
            val cPasswordVisibility = remember { mutableStateOf(true) }
            OutlinedTextField(
                value = confirmPassword.value,
                singleLine = true,
                onValueChange = {
                    if (confirmPasswordErrorState.value) {
                        confirmPasswordErrorState.value = false
                    }
                    confirmPassword.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                isError = confirmPasswordErrorState.value,
                label = {
                    Text(
                        text = "Confirm Password",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    errorLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    errorBorderColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        cPasswordVisibility.value = !cPasswordVisibility.value
                    }) {
                        Icon(
                            imageVector = if (cPasswordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "visibility",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
                visualTransformation = if (cPasswordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
            )
            if (confirmPasswordErrorState.value) {
                val msg = if (confirmPassword.value.isEmpty()) {
                    stringResource(id = R.string.msg_is_required).replace("##", "Confirm Password")
                } else if (confirmPassword.value != password.value) {
                   stringResource(id = R.string.msg_pwd_not_matching)
                } else {
                    ""
                }
                Text(text = msg, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.size(16.dp))
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = {
                    keyboardController?.hide()
                    when {
                        username.value.isEmpty() -> {
                            nameErrorState.value = true
                        }
                        password.value.isEmpty() -> {
                            passwordErrorState.value = true
                        }
                        confirmPassword.value.isEmpty() -> {
                            confirmPasswordErrorState.value = true
                        }
                        confirmPassword.value != password.value -> {
                            confirmPasswordErrorState.value = true
                        }
                        else -> onRegisterClick(username.value, password.value)
                    }
                },
                content = {
                    Text(text = stringResource(id = R.string.bt_register), color = Color.White)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = onLoginPress) {
                    Text(text = stringResource(id = R.string.bt_back_login), color = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(Modifier.size(16.dp))
        }
    }
}

@Composable
@Preview(name = " Light")
private fun PreviewLight() {
    ComposeTheme() {
        RegisterScreen("",onLoginPress = {}, onRegisterClick =  { username, password ->
        })
    }
}

@Composable
@Preview(name = " Dark")
private fun PreviewDark() {
    ComposeTheme(darkTheme = true) {
        RegisterScreen("", onLoginPress = {}, onRegisterClick =  { username, password ->
        })
    }
}