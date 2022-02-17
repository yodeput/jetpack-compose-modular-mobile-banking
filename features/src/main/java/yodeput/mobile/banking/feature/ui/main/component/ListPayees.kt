package yodeput.mobile.banking.feature.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import yodeput.mobile.banking.common.theme.ComposeTheme
import yodeput.mobile.banking.core.model.Payees

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPayees(
    list: List<Payees>,
    onItemClick: (Payees) -> Unit
) {
    LazyColumn {
        items(
            items = list,
            itemContent = { item ->
                val index: Int = list.indexOf(item)
                Item(item = item, onItemClick = { item ->
                    onItemClick(item)
                })
                if (list.size != index + 1) Divider(
                    modifier = Modifier.padding(
                        start = 40.dp,
                        end = 20.dp
                    )
                )
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Item(
    item: Payees,
    onItemClick: (Payees) -> Unit
) {
    val typography = MaterialTheme.typography
    Card(
        modifier = Modifier.padding(horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(10.dp),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onItemClick(item) },
                    indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            PayeesAvatar(item)
        }
    }
}

@Composable
fun PayeesAvatar(item : Payees){
    ConstraintLayout(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        val (column) = createRefs()

        Box(modifier = Modifier
            .wrapContentHeight()
            .constrainAs(column) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start, 0.dp)
            }) {
            Row() {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    if (item.name != null && item.name!!.isNotEmpty()) {
                        Text(
                            text = item.name!!.first().uppercase(),
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
                Column(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = item.name!!,
                        style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier
                            .wrapContentWidth()
                    )
                    Text(
                        text = item.accountNo!!,
                        style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier
                            .wrapContentWidth()
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
        Item(Payees.mock()){}
    }
}
@Composable
@Preview(name = " Dark")
private fun PreviewDark() {
    ComposeTheme(darkTheme = true) {
        Item(Payees.mock()){}
    }
}