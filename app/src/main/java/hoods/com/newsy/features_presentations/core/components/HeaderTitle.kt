package hoods.com.newsy.features_presentations.core.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import hoods.com.newsy.features_presentations.core.ui.theme.NewsyTheme

@Composable
fun HeaderTitle(
    title: String,
    icon: ImageVector,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(
            horizontal = defaultPadding
        )
    ) {
        TitleText(text = title)
        Spacer(modifier = Modifier.size(defaultPadding))
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.error
        )
    }

}

@Composable
fun TitleText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun PrevHeaderTitle() {
    NewsyTheme {
        HeaderTitle(
            title = "Title",
            icon = Icons.Default.Home
        )
    }
}