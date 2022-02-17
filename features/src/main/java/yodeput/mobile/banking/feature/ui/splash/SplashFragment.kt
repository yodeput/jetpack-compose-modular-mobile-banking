package yodeput.mobile.banking.feature.ui.splash

import android.content.pm.PackageManager
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import yodeput.mobile.banking.feature.base.BaseFragment
import yodeput.mobile.banking.feature.ui.auth.AuthViewModel
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.response.BalanceResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import yodeput.mobile.banking.common.theme.ComposeTheme
import yodeput.mobile.banking.feature.list.R

@AndroidEntryPoint
class SplashFragment : BaseFragment() {
    private val authModel: AuthViewModel by viewModels()


    @Composable
    override fun setContent() {
        val isLoggedIn = authModel.isLoggedIn.value
        val balanceData = authModel.balanceState.collectAsState().value

        SplashScreen(
            timer = 1000L,
            version = getVersion(),
            isLoggedIn,
            balanceData
        )
        findNavController().enableOnBackPressed(false)
    }


    fun getVersion(): String {
        try {
            val pInfo = requireContext().packageManager.getPackageInfo(
                requireContext().packageName, 0
            )
            val version = pInfo.versionName
            val verCode = pInfo.versionCode
            return "V $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return "V"
        }
    }

    @Composable
    fun SplashScreen(
        timer: Long,
        version: String,
        isLoggedIn: Boolean,
        balanceData: ViewState<BalanceResponse>
    ) {
        if (isLoggedIn) {
            when (balanceData) {
                is ViewState.Idle -> {
                }
                is ViewState.Loading -> {
                }
                is ViewState.Success -> {
                    delayFunction(timer = timer, nav = SplashFragmentDirections.actionSplashToMain())
                }
                is ViewState.Error -> {
                    if ((balanceData).errorCode?.message!! == "TokenExpiredError") {
                        findNavController().navigate(SplashFragmentDirections.actionSplashToLogin())
                        showToast( stringResource(id = R.string.msg_token_expired),
                            Toast.LENGTH_SHORT)
                    }
                }
            }
        } else {
            delayFunction(timer = timer, nav = SplashFragmentDirections.actionSplashToLogin())
        }

        val bg = if (isSystemInDarkTheme()) R.drawable.bg_only_dark else R.drawable.bg_only_light
        val logo = if (isSystemInDarkTheme()) R.drawable.logo_light else R.drawable.logo_app
        Box( modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
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
                    .fillMaxWidth()
                    .align(
                        Alignment.BottomCenter
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                CircularProgressIndicator(
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.primary

                )
                Spacer(Modifier.size(16.dp))
                Spacer(Modifier.size(16.dp))
                Spacer(Modifier.size(16.dp))
                Text(version, color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier)
                Spacer(Modifier.size(16.dp))
                Spacer(Modifier.size(16.dp))
            }
        }
    }

    @Composable
    fun delayFunction (
        timer: Long,
        nav: NavDirections
    ) {
        val scale = remember {
            Animatable(0f)
        }
        LaunchedEffect(key1 = true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec = tween(
                    durationMillis = 800,
                    easing = {
                        OvershootInterpolator(4f).getInterpolation(it)
                    })
            )
            delay(timer)
            findNavController().navigate(nav)
        }
    }

    @Composable
    @Preview(name = " Light")
    private fun PreviewLight() {
        ComposeTheme() {
            SplashScreen(
                timer = 1000L,
                version = "1.0.0",
                false,
                ViewState.Loading
            )
        }
    }
    @Composable
    @Preview(name = " Dark")
    private fun PreviewDark() {
        ComposeTheme(darkTheme = true) {
            SplashScreen(
                timer = 1000L,
                version = "1.0.0",
                false,
                ViewState.Loading
            )
        }
    }
}