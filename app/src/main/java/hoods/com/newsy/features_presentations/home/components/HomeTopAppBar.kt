package hoods.com.newsy.features_presentations.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import hoods.com.newsy.R
import hoods.com.newsy.features_presentations.core.ui.theme.NewsyTheme
import hoods.com.newsy.utils.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    modifier: Modifier = Modifier,
    openDrawer: () -> Unit,
    onSearch: () -> Unit,
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    scrollBehaviour: TopAppBarScrollBehavior = TopAppBarDefaults
        .enterAlwaysScrollBehavior(topAppBarState),
) {
    val title = "Newsy"
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(R.drawable.ic_newsy_water_mark),
                contentDescription = title,
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = openDrawer, modifier = Modifier.testTag(TestTags.DrawerBtn)) {
                Icon(
                    painterResource(R.drawable.ic_newsy_logo),
                    contentDescription = "navigation",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        actions = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "navigation",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PrevHomeTopAppBar() {
    NewsyTheme {
        HomeTopAppBar(
            openDrawer = {},
            onSearch = {},
        )
    }
}