package yodeput.mobile.banking.feature.ui.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.repository.UserRepository
import yodeput.mobile.banking.core.request.LoginRequest
import yodeput.mobile.banking.core.response.BalanceResponse
import yodeput.mobile.banking.core.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject
constructor(private val repository: UserRepository) : ViewModel() {

    val _loginState = MutableStateFlow<ViewState<LoginResponse>>(ViewState.Idle)
    val loginState: StateFlow<ViewState<LoginResponse>>
        get() = _loginState

    val _registerState = MutableStateFlow<ViewState<LoginResponse>>(ViewState.Idle)
    val registerState: StateFlow<ViewState<LoginResponse>>
        get() = _registerState

    private val _balanceState = MutableStateFlow<ViewState<BalanceResponse>>(ViewState.Idle)
    val balanceState: StateFlow<ViewState<BalanceResponse>>
        get() = _balanceState

    val isLoggedIn = mutableStateOf(false)
        get() = field


    init {
        checkLogin()
    }

    fun performLogin(username: String, password: String) {
        _loginState.tryEmit(ViewState.Idle)
        viewModelScope.launch {
            repository.loginApi(LoginRequest(username = username, password = password)).collect {
                _loginState.tryEmit(it)
                if (it is ViewState.Success) {
                    getBalance()
                }
            }
        }
    }

    fun performRegister(username: String, password: String) {
        _loginState.tryEmit(ViewState.Idle)
        viewModelScope.launch {
            repository.registerApi(LoginRequest(username = username, password = password)).collect {
                _registerState.tryEmit(it)
            }
        }
    }

    private fun checkLogin() {
        viewModelScope.launch {
            isLoggedIn.value = repository.isLoggedIn()
            if(isLoggedIn.value){
                getBalance()
            }
        }
    }

    fun getBalance() {
        viewModelScope.launch {
            repository.getBalanceApi().collect {
                _balanceState.tryEmit(it)
            }
        }
    }
}
