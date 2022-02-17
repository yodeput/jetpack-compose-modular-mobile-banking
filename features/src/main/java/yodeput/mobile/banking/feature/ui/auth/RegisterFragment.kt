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
import yodeput.mobile.banking.feature.ui.auth.component.RegisterScreen

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    private val viewModel: AuthViewModel by viewModels()


    @Composable
    override fun setContent() {
        val viewState = viewModel.registerState.collectAsState().value
        val dialogShow = remember { mutableStateOf(false) }
        val clearForm = remember { mutableStateOf("") }

        val titleDialog = remember { mutableStateOf("") }
        val messageDialog = remember { mutableStateOf("") }


        RegisterScreen(clearForm.value, onLoginPress = {
            findNavController().navigateUp()
        }) { username, password ->
            viewModel.performRegister(username, password)
        }
        when (viewState) {
            is ViewState.Loading -> ProgressScreen()
            is ViewState.Success -> {
                // SHOW dialog Success
                titleDialog.value = "Registration"
                messageDialog.value = "Your account have been created"
                dialogShow.value = true
                // CLEAR FORM
                clearForm.value = "CLEAR"
            }
            is ViewState.Error -> {
                // SHOW dialog Error
                titleDialog.value = "Registration"
                messageDialog.value = viewState.errorCode?.message!!
                dialogShow.value = true
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