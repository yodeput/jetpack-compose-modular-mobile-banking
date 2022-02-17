package yodeput.android.sample.app.feature

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.collectAsState
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.dao.PayeeDao
import yodeput.mobile.banking.core.dao.TransactionDao
import yodeput.mobile.banking.core.dao.UserDao
import yodeput.mobile.banking.core.database.PreferenceStorage
import yodeput.mobile.banking.core.network.AuthService
import yodeput.mobile.banking.core.repository.UserRepository
import yodeput.mobile.banking.core.request.LoginRequest
import yodeput.mobile.banking.core.response.LoginResponse
import yodeput.mobile.banking.feature.ui.auth.AuthViewModel

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var api: AuthService

    @Mock
    private lateinit var userDao: UserDao

    @Mock
    private lateinit var transactionDao: TransactionDao

    @Mock
    private lateinit var payeeDao: PayeeDao

    @Mock
    private lateinit var viewModel: AuthViewModel

    @Mock
    private lateinit var context: Context

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val pref = PreferenceStorage.getInstance(context)
        val repository =
            UserRepository(pref, userDao, transactionDao, payeeDao, api, Dispatchers.Main)
        viewModel = AuthViewModel(repository)
    }

    @Test
    fun login_shouldReturnSuccess() {
        testCoroutineRule.runBlockingTest {
            doReturn(ApiResponse)
                .`when`(viewModel)
                .performLogin("test", "asdasd")
        }


        testCoroutineRule.pauseDispatcher()
        assertThat(viewModel.loginState.value, `is`(ViewState.Loading))

        testCoroutineRule.resumeDispatcher()
        assertThat(viewModel.loginState.value, `is`(ViewState.Success(LoginResponse())))
    }
}
