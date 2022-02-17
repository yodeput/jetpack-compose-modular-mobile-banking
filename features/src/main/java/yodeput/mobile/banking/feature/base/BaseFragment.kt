package yodeput.mobile.banking.feature.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import yodeput.mobile.banking.common.theme.ComposeTheme
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.common.util.composeView
import yodeput.mobile.banking.feature.list.R
import yodeput.mobile.banking.feature.ui.auth.AuthViewModel
import yodeput.mobile.banking.feature.ui.component.DialogMessage

abstract class BaseFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = composeView {

        ComposeTheme {
            setContent()
        }
    }

    @Composable
    abstract fun setContent(): Unit

    @Composable
    open fun openDialog(isShow: Boolean, title:String, message: String, onDismiss: () -> Unit){
        if(isShow) {
            DialogMessage(
                title = title,
                message = message,
                onDismiss = {
                    onDismiss()
                })
        }
    }

    @Composable
    open fun showToast(message: String, duration: Int ){
        Toast.makeText(
            context,
            message,
            duration
        ).show()
    }
}