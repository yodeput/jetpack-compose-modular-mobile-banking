package yodeput.mobile.banking.feature.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.model.Balance
import yodeput.mobile.banking.core.model.Payees
import yodeput.mobile.banking.core.model.Transaction
import yodeput.mobile.banking.core.model.User
import yodeput.mobile.banking.core.repository.PayeeRepository
import yodeput.mobile.banking.core.repository.TransactionRepository
import yodeput.mobile.banking.core.repository.UserRepository
import yodeput.mobile.banking.core.request.TransferRequest
import yodeput.mobile.banking.core.response.TransferResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject
constructor(private val userRepo: UserRepository, private val trxRepository: TransactionRepository, private val payeeRepository: PayeeRepository) : ViewModel() {

    private val _userBalance = MutableStateFlow(Balance())
    val userBalance: StateFlow<Balance>
        get() = _userBalance

    private val _trxListState = MutableStateFlow<ViewState<List<Transaction>>>(ViewState.Idle)
    val trxListState: StateFlow<ViewState<List<Transaction>>>
        get() = _trxListState

    private val _transferState = MutableStateFlow<ViewState<TransferResponse>>(ViewState.Idle)
    val transferState: StateFlow<ViewState<TransferResponse>>
        get() = _transferState

    private val _payeesListState = MutableStateFlow<ViewState<List<Payees>>>(ViewState.Idle)
    val payeesListState: StateFlow<ViewState<List<Payees>>>
        get() = _payeesListState

    private val _isRefreshTrx = MutableStateFlow(false)
    val isRefreshTrx: StateFlow<Boolean>
        get() = _isRefreshTrx.asStateFlow()

    private val _logoutState = MutableStateFlow<ViewState<Boolean>>(ViewState.Idle)
    val logoutState: StateFlow<ViewState<Boolean>>
        get() = _logoutState

    private val _user = MutableStateFlow<ViewState<User>>(ViewState.Idle)
    val user: StateFlow<ViewState<User>>
        get() = _user.asStateFlow()

    private val _payeeSelected = MutableStateFlow(Payees())
    val payeeSelected: StateFlow<Payees>
        get() = _payeeSelected.asStateFlow()

    init {
        getUser()
        getBalance()
        getTransaction()
        getPayees()
    }

    fun getUser() {
        viewModelScope.launch {
            userRepo.getUser().collect { _user.tryEmit(it) }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logout().collect{
                _logoutState.tryEmit(it)
            }
        }
    }

    fun getBalance(isRefresh: Boolean = false) {
        viewModelScope.launch {
            userRepo.getBalance(isRefresh).collect {
                _userBalance.tryEmit(it!!)
            }
        }
    }


    fun getTransaction(refresh: Boolean = false) {
        viewModelScope.launch {
            _isRefreshTrx.emit(true)
            trxRepository.getData(isRefresh = refresh).collect {
                _trxListState.tryEmit(it)
            }
            _isRefreshTrx.emit(false)
        }
    }

    fun getPayees(refresh: Boolean = false) {
        viewModelScope.launch {
            _isRefreshTrx.emit(true)
            payeeRepository.getData(isRefresh = refresh).collect {
                _payeesListState.tryEmit(it)
            }
            _isRefreshTrx.emit(false)
        }
    }

    fun setPayee(payees: Payees) {
        _payeeSelected.tryEmit(payees)
    }

    fun transfer(body: TransferRequest) {
        viewModelScope.launch {
            trxRepository.transferApi(body).collect {
                _transferState.tryEmit(it)
                if(it is ViewState.Success){
                    getBalance(true)
                    getTransaction(true)
                }
            }
        }
    }

}