package yodeput.mobile.banking.feature.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import yodeput.mobile.banking.common.theme.ComposeTheme
import yodeput.mobile.banking.common.theme.Green700
import yodeput.mobile.banking.core.model.Transaction


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListTransaction(
    list: List<Transaction>,
    onItemClick: (Transaction) -> Unit
) {
    val grouped = mutableListOf<List<Transaction>>()
    val listSize = remember { mutableStateOf(0) }
    val byDate = list.groupBy {
        it.dateFormated
    }

    byDate.onEachIndexed { index, entry ->
        if (index < 2) grouped.add(entry.value)
        listSize.value = byDate.size
    }
    ComposeTheme {LazyColumn {
        items(
            items = grouped.toList(),
            itemContent = { groupedItem ->
                Card(
                    containerColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    shape = RoundedCornerShape(28.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                    ) {
                        Text(
                            groupedItem[0].dateHumanFormated,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        )
                        Column {
                            groupedItem.forEachIndexed { index, transaction ->
                                Item(item = transaction, onItemClick = onItemClick)
                                if (groupedItem.size != index + 1) Divider(
                                    modifier = Modifier.padding(
                                        horizontal = 20.dp
                                    )
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.size(8.dp))
            }
        )
        if (listSize.value > 2) {
            item {
                Button(
                    onClick = {
                    },
                    shape = RoundedCornerShape(28.dp),
                    content = {
                        Text(text = "See All Transactions".uppercase(), color = MaterialTheme.colorScheme.onPrimary)
                    },
                    contentPadding = PaddingValues(vertical = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            }
        } else {
            item {
                Spacer(Modifier.size(20.dp))
            }
        }
    }}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Item(
    item: Transaction,
    onItemClick: (Transaction) -> Unit
) {
    val typography = MaterialTheme.typography
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(10.dp),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { onItemClick(item) },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                val (column, text1) = createRefs()
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .wrapContentWidth()
                        .constrainAs(column) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    if (item.transactionType == "received") {
                        Text(
                            text = item.transactionType!!.replaceFirstChar { it.uppercase() },
                            style = typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    } else {
                        Text(
                            text = item.receipient?.accountHolder ?: "",
                            style = typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = item.receipient?.accountNo ?: "",
                            style = typography.labelSmall
                        )
                    }
                }
                val textColor = if (item.transactionType == "transfer") MaterialTheme.colorScheme.onBackground else Green700
                    val textSuffix = if (item.transactionType == "transfer") "-" else "+"

                Box(Modifier.wrapContentWidth().wrapContentHeight()
                    .clip(RoundedCornerShape(7.dp))
                    .background(textColor.copy(alpha = 0.08f)).constrainAs(text1) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.bottom)
                    bottom.linkTo(parent.top)
                }){
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                        text = "${textSuffix}${item.amountFormated}",
                        style = typography.titleSmall.copy(color = textColor)
                    )
                }
            }
        }
    }
}

@Composable
@Preview(name = " Light")
private fun PreviewLight() {
    ComposeTheme() {
        Item(Transaction.mock()){}
    }
}
@Composable
@Preview(name = " Dark")
private fun PreviewDark() {
    ComposeTheme(darkTheme = true) {
        Item(Transaction.mock()){}
    }
}