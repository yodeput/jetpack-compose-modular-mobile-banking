package yodeput.mobile.banking.feature.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import yodeput.mobile.banking.feature.base.BaseFragment
import yodeput.mobile.banking.feature.ui.common.ProgressScreen
import yodeput.mobile.banking.feature.ui.component.DialogMessage
import yodeput.mobile.banking.common.util.ViewState
import dagger.hilt.android.AndroidEntryPoint
import yodeput.mobile.banking.feature.ui.auth.component.LoginScreen

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val viewModel: AuthViewModel by viewModels()

    @Composable
    override fun setContent() {
        LoginScreen(findNavController()) { username, password ->
            viewModel.performLogin(username, password)
        }
        val dialogShow = remember { mutableStateOf(false) }
        val titleDialog = remember { mutableStateOf("") }
        val messageDialog = remember { mutableStateOf("") }

        val loginState = viewModel.loginState.collectAsState().value
        when (loginState) {
            is ViewState.Loading -> ProgressScreen()
            is ViewState.Success -> {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            }
            is ViewState.Error -> {
                dialogShow.value = true
                titleDialog.value = "Login"
                messageDialog.value = (loginState as ViewState.Error).errorCode?.message!!
            }
        }

        openDialog(
            isShow = dialogShow.value,
            title = titleDialog.value,
            message = messageDialog.value
        ) {
            dialogShow.value = false
            viewModel._loginState.tryEmit(ViewState.Idle)
        }
    }
}