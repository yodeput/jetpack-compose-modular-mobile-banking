package yodeput.mobile.banking.feature.ui.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ElectricalServices
import androidx.compose.material.icons.twotone.Send
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import yodeput.mobile.banking.common.theme.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import yodeput.mobile.banking.core.model.User
import yodeput.mobile.banking.feature.ui.common.UserAvatar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceLayout(
    balance: String,
    sendMoneyPress: () -> Unit,
    payBillsPress: () -> Unit,
) {
    ConstraintLayout(
    modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        val (card, action) = createRefs()
        Card(
            modifier = Modifier.constrainAs(card) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            shape = RoundedCornerShape(28.dp),
            containerColor = Color.Transparent,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            startY = 0.5F,
                            colors = listOf(
                                MaterialTheme.colorScheme.onPrimaryContainer,
                                MaterialTheme.colorScheme.error,
                            )
                        )
                    )
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .placeholder(
                                visible = balance.isEmpty(),
                                color = Red200,
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Red50,
                                ),
                                shape = RoundedCornerShape(4.dp),
                            ),
                        text = "Your balance is",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSecondary,
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.size(15.dp))
                    Text(
                        text = "SGD ${balance}",
                        maxLines = 1,
                        modifier = Modifier
                            .placeholder(
                                visible = balance.isEmpty(),
                                color = Red200,
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Red50,
                                ),
                                shape = RoundedCornerShape(4.dp),
                            )
                            .wrapContentWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            color = MaterialTheme.colorScheme.onSecondary,
                        ),
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.size(25.dp))
                }

            }
        }
        Card(
            modifier = Modifier
                .width(220.dp)
                .constrainAs(action) {
                    top.linkTo(card.bottom)
                    bottom.linkTo(card.bottom)
                    start.linkTo(card.end)
                    end.linkTo(card.start)
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(28.dp),
        ) {
            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                val (sendMo, separator, payBil) = createRefs()
                createHorizontalChain(
                    sendMo,
                    separator,
                    payBil,
                    chainStyle = ChainStyle.Spread
                )
                Card(
                    modifier = Modifier
                        .padding(0.dp)
                        .constrainAs(sendMo) {},
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(28.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .clickable(onClick = sendMoneyPress,
                                indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                                interactionSource = remember { MutableInteractionSource() })
                    ) {
                        Spacer(Modifier.size(3.dp))
                        Icon(
                            Icons.TwoTone.Send, null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.size(10.dp))
                        Text(
                            "Send Money",
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.size(3.dp))
                    }
                }
                Box(
                    Modifier
                        .constrainAs(separator) {
                            top.linkTo(parent.bottom)
                            bottom.linkTo(parent.top)
                        }
                        .width(2.dp)
                        .height(40.dp)
                        .background(
                            Color.Gray.copy(alpha = 0.3F),
                            shape = RoundedCornerShape(
                                28.dp
                            )
                        ))

                Card(
                    modifier = Modifier
                        .padding(0.dp)
                        .constrainAs(payBil) {},
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(28.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(100.dp)
                            .clickable(onClick = payBillsPress,
                                indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                                interactionSource = remember { MutableInteractionSource() })
                    ) {
                        Spacer(Modifier.size(3.dp))
                        Icon(
                            Icons.TwoTone.ElectricalServices, null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.size(10.dp))
                        Text(
                            "Pay Bills",
                            textAlign = TextAlign.Center,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Spacer(Modifier.size(3.dp))
                    }
                }
            }

        }
    }
}

@Composable
@Preview(name = "BalanceLayout Light")
private fun UserAvatarPreviewLight() {
    ComposeTheme() {
        BalanceLayout(
            balance = "$120.00.",
            sendMoneyPress = {},
            payBillsPress = {},
        )
    }
}
@Composable
@Preview(name = "BalanceLayout Dark")
private fun UserAvatarPreviewDark() {
    ComposeTheme(darkTheme = true) {
        BalanceLayout(
            balance = "$120.00.",
            sendMoneyPress = {},
            payBillsPress = {},
        )
    }
}