package yodeput.mobile.banking.feature.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import yodeput.mobile.banking.feature.ui.component.ListPayees
import yodeput.mobile.banking.feature.ui.component.PayeesAvatar
import yodeput.mobile.banking.common.util.CurrencyVisualTransformation
import yodeput.mobile.banking.common.util.NumberUtils.currencyFormat
import yodeput.mobile.banking.common.util.NumberUtils.isNumber
import yodeput.mobile.banking.core.model.Payees
import yodeput.mobile.banking.core.request.TransferRequest

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    state: ModalBottomSheetState,
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheetLayout(
        sheetElevation = 15.dp,
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        sheetState = state,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        sheetContent = content
    ) {}
}


@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SheetSendMoney(
    balance: Double,
    state: ModalBottomSheetState,
    contact: Payees,
    onClosePress: () -> Unit,
    onSubmitPress: (TransferRequest) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val amount = remember { mutableStateOf("") }
    val amountErrorState = remember { mutableStateOf(false) }
    val amountErrorStringState = remember { mutableStateOf("") }

    val description = remember { mutableStateOf("") }

    if (state.isVisible) {
        amount.value = ""
        amountErrorState.value = false
        amountErrorStringState.value = ""
        description.value = ""
    }
    BottomSheet(
        state = state,
    ) {
        TopAppBar(
            modifier = Modifier
                .padding(top = 0.dp)
                .height(70.dp),
            elevation = 0.dp,
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            title = {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 5.dp)
                ) {
                    PayeesAvatar(contact)
                }
            },
            actions = {
                IconButton(modifier = Modifier
                    .padding(end = 10.dp), onClick = {
                    keyboardController?.hide()
                    onClosePress()
                }) {
                    Icon(
                        Icons.Filled.Close, null,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            },
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 25.dp)
        ) {
            Spacer(Modifier.size(5.dp))
            OutlinedTextField(
                value = amount.value,
                singleLine = true,
                onValueChange = {
                    if (amountErrorState.value) {
                        amountErrorState.value = false
                    }
                    if (it.isNotEmpty()) {
                        if (isNumber(it)) {
                            if (it.toInt() < 5) {
                                amountErrorState.value = true
                                amountErrorStringState.value = "Minimum transfer is $5"
                            } else if (it.toDouble() > 10000.00) {
                                amountErrorState.value = true
                                amountErrorStringState.value =
                                    "Maximum daily transfer is ${currencyFormat(10000.00)}"
                            } else if (it.toDouble() > balance) {
                                amountErrorState.value = true
                                amountErrorStringState.value =
                                    "Your balance is ${currencyFormat(balance)}"
                            } else {
                                amountErrorState.value = false
                            }
                            amount.value = it.filter { a -> a.isDigit() }
                        }
                    }

                },
                visualTransformation = CurrencyVisualTransformation(),
                isError = amountErrorState.value,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    androidx.compose.material3.Text(
                        text = "Amount",
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                leadingIcon = {
                    androidx.compose.material3.Text(
                        text = "$",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                },
                textStyle = MaterialTheme.typography.headlineMedium,
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
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )
            if (amountErrorState.value) {
                androidx.compose.material3.Text(
                    text = amountErrorStringState.value,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(Modifier.size(16.dp))
            OutlinedTextField(
                value = description.value,
                singleLine = false,
                onValueChange = {
                    description.value = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    androidx.compose.material3.Text(
                        text = "Description",
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
                keyboardActions = KeyboardActions(
                    onDone = { keyboardController?.hide() }
                ),
            )
            Spacer(Modifier.size(16.dp))
            Spacer(Modifier.size(16.dp))
            androidx.compose.material3.Button(
                onClick = {
                    when {
                        amount.value.isEmpty() -> {
                            amountErrorState.value = true
                            amountErrorStringState.value = "Amount is Required"
                        }
                        else -> {
                            val data = TransferRequest(
                                amount = amount.value.toInt(),
                                description = description.value,
                                receipientAccountNo = contact.accountNo
                            )
                            if (!amountErrorState.value) {
                                keyboardController?.hide()
                                onSubmitPress(data)
                            }


                        }
                    }

                },
                shape = RoundedCornerShape(28.dp),
                content = {
                    androidx.compose.material3.Text(text = "NEXT", color = Color.White)
                },
                contentPadding = PaddingValues(vertical = 10.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.size(16.dp))
            Spacer(Modifier.size(16.dp))
        }
    }
}


@OptIn(ExperimentalMaterialApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun SheetContact(
    list: List<Payees>,
    state: ModalBottomSheetState,
    onClosePress: () -> Unit,
    onSelectPress: (Payees) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    BottomSheet(
        state = state,
    ) {
        TopAppBar(
            modifier = Modifier
                .padding(top = 0.dp)
                .height(70.dp),
            elevation = 0.dp,
            backgroundColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            title = {
                Text("Contact", modifier = Modifier
                    .padding(start = 10.dp))
            },
            actions = {
                IconButton(modifier = Modifier
                    .padding(end = 10.dp), onClick = {
                    keyboardController?.hide()
                    onClosePress()
                }) {
                    Icon(
                        Icons.Filled.Close, null,
                        tint = MaterialTheme.colorScheme.onBackground,
                    )
                }
            },
        )
        ListPayees(list) {
            onSelectPress(it)
        }
        Spacer(Modifier.size(16.dp))
        Spacer(Modifier.size(16.dp))
    }
}

