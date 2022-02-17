package yodeput.mobile.banking.feature.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import yodeput.mobile.banking.feature.base.BaseFragmentLifeCycle
import yodeput.mobile.banking.feature.ui.common.ProgressScreen
import yodeput.mobile.banking.feature.ui.common.SheetContact
import yodeput.mobile.banking.feature.ui.common.SheetSendMoney
import yodeput.mobile.banking.feature.ui.component.*
import yodeput.mobile.banking.feature.ui.main.component.BalanceLayout
import yodeput.mobile.banking.feature.ui.main.component.TopLayout
import yodeput.mobile.banking.feature.ui.main.component.TransactionAndContact
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.feature.list.R

@AndroidEntryPoint
class MainFragment : BaseFragmentLifeCycle() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(
        ExperimentalMaterial3Api::class,
        androidx.compose.material.ExperimentalMaterialApi::class
    )
    @Composable
    override fun setContent() {
        val logoutState = viewModel.logoutState.collectAsState().value
        val trxListState = viewModel.trxListState.collectAsState().value
        val transferState = viewModel.transferState.collectAsState().value
        val payeesListState = viewModel.payeesListState.collectAsState().value
        val dialogLogout = remember { mutableStateOf(false) }
        val userDataState = viewModel.user.collectAsState().value
        val userBalance = viewModel.userBalance.collectAsState().value

        val scope = rememberCoroutineScope()
        val sendMoneySheet = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
        )
        val payeeSelected = viewModel.payeeSelected.collectAsState().value

        val contactSheet = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden
        )


        val selectedTab = remember { mutableStateOf(0) }

        when (logoutState) {
            is ViewState.Loading -> ProgressScreen()
            is ViewState.Success -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
            }
        }

        val bg = if (isSystemInDarkTheme()) R.drawable.bg_only_dark else R.drawable.bg_only_light


        Scaffold(modifier = Modifier.padding(start = 0.dp, end = 0.dp, top = 0.dp))
        {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Column(
                ) {
                    TopLayout(state = userDataState,
                        logoutPress = {
                            dialogLogout.value = true
                        })
                    Spacer(Modifier.size(25.dp))
                    BalanceLayout(
                        balance = userBalance.balance ?: "$0",
                        sendMoneyPress = {
                                         scope.launch { contactSheet.show() }
                        },
                        payBillsPress = {},
                    )
                    Spacer(Modifier.size(20.dp))
                    TransactionAndContact(
                        viewModel, trxListState, payeesListState,
                        onTransactionPress = {
                            scope.launch { sendMoneySheet.show() }
                        },
                        onContactPress = {
                            viewModel.setPayee(it)
                            scope.launch { sendMoneySheet.show() }
                        },
                        selectedTab = selectedTab
                    )
                }
            }
        }

        if (dialogLogout.value) {
            DialogConfirm(
                title = "Logout",
                message = "Are you sure want to Logout?",
                onConfirm = { viewModel.logout() },
                onDismiss = { dialogLogout.value = false }
            )
        }
        if (!payeeSelected.id.isNullOrEmpty()) {
            SheetSendMoney(
                balance = userBalance.balanceDouble!!,
                state = sendMoneySheet,
                contact = payeeSelected,
                onClosePress = {
                    scope.launch { sendMoneySheet.hide() }
                },
                onSubmitPress = {

                    viewModel.transfer(it)

                })
        }

        when (transferState) {
            is ViewState.Loading -> {
                ProgressScreen()
            }
            is ViewState.Success -> {
                val data = transferState.data
                scope.launch {
                    sendMoneySheet.hide()
                    sendMoneySheet.hide()
                }
                selectedTab.value = 0
            }
        }

        if (payeesListState is ViewState.Success) {
            SheetContact(
                list = payeesListState.data,
                state = contactSheet,
                onClosePress = {
                    scope.launch { contactSheet.hide() }
                },
                onSelectPress = {
                    viewModel.setPayee(it)
                    scope.launch {
                        contactSheet.hide()
                        sendMoneySheet.show()
                    }
                })
        }
    }
}


