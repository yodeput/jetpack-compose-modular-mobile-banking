package yodeput.mobile.banking.feature.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import yodeput.mobile.banking.feature.ui.component.UserAvatarShimmer
import yodeput.mobile.banking.common.theme.Grey400
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),
            shape = RoundedCornerShape(28.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier.padding(40.dp)
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary,)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingList() {
    LazyColumn(modifier = Modifier
        .padding(horizontal = 25.dp, vertical = 10.dp)) {
        item { LoadingListItem() }
        item { LoadingListItem() }
        item { LoadingListItem() }
        item { LoadingListItem() }
        item { LoadingListItem() }
        item { LoadingListItem() }
        item { LoadingListItem() }
        item { LoadingListItem() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingListPayees() {
    LazyColumn(modifier = Modifier
        .padding(horizontal = 9.dp, vertical = 0.dp)) {
        item { LoadingPayeesItem() }
        item { LoadingPayeesItem() }
        item { LoadingPayeesItem() }
        item { LoadingPayeesItem() }
        item { LoadingPayeesItem() }
        item { LoadingPayeesItem() }
        item { LoadingPayeesItem() }
        item { LoadingPayeesItem() }
    }
}

@Composable
fun LoadingListItem(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val typography = MaterialTheme.typography
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
                Text(
                    modifier = Modifier.placeholder(
                        visible = true,
                        color = Grey400,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.White
                        ),
                        shape = RoundedCornerShape(4.dp),
                    ),
                    text = "Yogi Dewansyah Putra",
                    style = typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(modifier = Modifier.padding(top=3.dp).placeholder(
                    visible = true,
                    color = Grey400,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp),
                ),
                    text = "123-345-7890",
                    style = typography.labelSmall
                )
            }

            Text(
                modifier = Modifier.constrainAs(text1) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.bottom)
                    bottom.linkTo(parent.top)
                }.placeholder(
                    visible = true,
                    color = Grey400,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp),
                ),
                text = "$1,1000,0",
                style = typography.titleSmall
            )
        }
    }
}

@Composable
fun LoadingPayeesItem(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val typography = MaterialTheme.typography
        ConstraintLayout(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            UserAvatarShimmer()
        }
    }
}