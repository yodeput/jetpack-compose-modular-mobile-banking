package yodeput.mobile.banking.feature.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import yodeput.mobile.banking.common.theme.Grey400
import yodeput.mobile.banking.common.theme.Red200
import yodeput.mobile.banking.common.theme.Red50
import yodeput.mobile.banking.core.model.User
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer


@Composable
fun UserAvatar (
    user: User
) {
    Row() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            if (user.username != null && user.username!!.isNotEmpty()) {
                Text(
                    text = user.username!!.first().uppercase(),
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = user.username!!,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .wrapContentWidth()
                    .placeholder(
                        visible = false,
                        color = Red200,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Red50,
                        ),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
            Text(
                text = user.accountNo!!,
                style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                modifier = Modifier
                    .wrapContentWidth()
                    .placeholder(
                        visible = false,
                        color = Red200,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Red50,
                        ),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
        }
    }
}

@Composable
fun UserAvatarShimmer () {
    Row() {
        Box(
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .placeholder(
                    visible = true,
                    color = Grey400,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White,
                    ),
                    shape = CircleShape,
                ),
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = "Yogi Dewansyah",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .wrapContentWidth()
                    .placeholder(
                        visible = true,
                        color = Grey400,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
            Text(
                text = "123-456-789",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .wrapContentWidth().padding(top=3.dp)
                    .placeholder(
                        visible = true,
                        color = Grey400,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White,
                        ),
                        shape = RoundedCornerShape(4.dp),
                    ),
            )
        }
    }
}