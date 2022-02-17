package yodeput.mobile.banking.feature.ui.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import yodeput.mobile.banking.feature.ui.common.LoadingList
import yodeput.mobile.banking.feature.ui.common.LoadingListPayees
import yodeput.mobile.banking.feature.ui.component.ListPayees
import yodeput.mobile.banking.feature.ui.component.ListTransaction
import yodeput.mobile.banking.feature.ui.main.MainViewModel
import yodeput.mobile.banking.common.util.ViewState
import yodeput.mobile.banking.core.model.Payees
import yodeput.mobile.banking.core.model.Transaction
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun TransactionAndContact(
    viewModel: MainViewModel,
    trxListState: ViewState<List<Transaction>>,
    payeesListState: ViewState<List<Payees>>,
    onTransactionPress: (Transaction) -> Unit,
    onContactPress: (Payees) -> Unit,
    selectedTab:  MutableState<Int>
) {
    val tabs = listOf(
        "Transactions",
        "Contact",
    )
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Box(Modifier.padding(horizontal = 50.dp)) {
            TabRow(
                selectedTabIndex = selectedTab.value,
                indicator = { },
                divider = {}
            ) {
                tabs.forEachIndexed { index, text ->
                    Tab(
                        selectedContentColor = MaterialTheme.colorScheme.background,
                        modifier = Modifier.background(MaterialTheme.colorScheme.background),
                        selected = selectedTab.value == index,
                        onClick = {
                            selectedTab.value = index
                        }) {
                        ChoiceChipContent(
                            text = text,
                            selected = index == selectedTab.value,
                            modifier = Modifier
                                .padding(horizontal = 0.dp, vertical = 10.dp)
                                .defaultMinSize(minWidth = 110.dp)
                        )
                    }
                }
            }
        }
        when (selectedTab.value) {
            0 -> {
                when (trxListState) {
                    is ViewState.Loading -> LoadingList()
                    is ViewState.Success -> {
                        val isRefreshTrx by viewModel.isRefreshTrx.collectAsState()
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isRefreshing = isRefreshTrx),
                            onRefresh = { viewModel.getTransaction(true) },
                            indicator = { state, trigger ->
                                SwipeRefreshIndicator(
                                    state = state,
                                    refreshTriggerDistance = trigger,
                                    scale = true,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(28.dp),
                                )
                            }
                        ) {
                            ListTransaction(
                                trxListState.data
                            ) {
                                onTransactionPress(it)
                            }
                        }
                    }
                    is ViewState.Error -> {

                    }
                }

            }
            else -> {
                when (payeesListState) {
                    is ViewState.Loading -> LoadingListPayees()
                    is ViewState.Success -> {
                        val isRefreshTrx by viewModel.isRefreshTrx.collectAsState()
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isRefreshing = isRefreshTrx),
                            onRefresh = { viewModel.getPayees(true) },
                            indicator = { state, trigger ->
                                SwipeRefreshIndicator(
                                    state = state,
                                    refreshTriggerDistance = trigger,
                                    scale = true,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    contentColor = MaterialTheme.colorScheme.background,
                                    shape = RoundedCornerShape(28.dp),
                                )
                            }
                        ) {
                            ListPayees(
                                payeesListState.data
                            ) {
                                onContactPress(it)
                            }
                        }
                    }
                    is ViewState.Error -> {

                    }
                }
            }

        }
    }
}

@Composable
private fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.primaryContainer
        },
        shape = RoundedCornerShape(28.dp),
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.width(120.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = when {
                        selected -> MaterialTheme.colorScheme.onPrimary
                        else -> MaterialTheme.colorScheme.onPrimaryContainer
                    }
                ),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 5.dp),
            )
        }
    }
}