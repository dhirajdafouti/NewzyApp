package hoods.com.newsy.features_presentations.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hoods.com.newsy.R
import hoods.com.newsy.features_components.core.domain.models.Country
import hoods.com.newsy.features_components.core.domain.models.Language
import hoods.com.newsy.features_presentations.setting.viewmodel.SettingState
import hoods.com.newsy.features_presentations.setting.viewmodel.SettingViewModel
import hoods.com.newsy.features_presentations.setting.viewmodel.SettingsEvents
import hoods.com.newsy.utils.Utils

@Composable
fun SettingScreen(
    settingViewModel: SettingViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val state = settingViewModel.settingState

    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.isError -> {
            Text(
                text = state.errorMessage ?: "unknown error",
                color = MaterialTheme.colorScheme.error
            )
        }

        else -> {
            SettingScreen(
                countryList = Utils.countryCodeList,
                languageList = Utils.languageCodeList,
                settingState = state,
                onCountryToggle = {
                    settingViewModel.onSettingEvents(
                        SettingsEvents.CountryChange(it)
                    )
                },
                onLanguageToggle = {
                    settingViewModel.onSettingEvents(
                        SettingsEvents.LanguageChange(it)
                    )
                },
                saveSetting = {
                    settingViewModel.onSettingEvents(
                        SettingsEvents.SaveSetting
                    )
                    navigateUp()
                },
                navigateUp = navigateUp
            )
        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    countryList: List<Country>,
    languageList: List<Language>,
    settingState: SettingState,
    onCountryToggle: (Int) -> Unit,
    onLanguageToggle: (Int) -> Unit,
    saveSetting: () -> Unit,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Settings Screen",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(navigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, "navigate")
                    }
                },
                actions = {
                    IconButton(saveSetting) {
                        Icon(imageVector = Icons.Default.Check, "save settings")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding
        ) {
            CountryItems(
                items = countryList,
                selectedCountryIndex = settingState.countryIndex,
                onToggle = {
                    onCountryToggle(it)
                }
            )
            LanguageItems(
                items = languageList,
                onToggle = {
                    onLanguageToggle(it)
                },
                selectedLanguageIndex = settingState.languageIndex
            )


        }
    }

}

private fun LazyListScope.CountryItems(
    items: List<Country>,
    selectedCountryIndex: Int,
    onToggle: (Int) -> Unit,
) {
    item {
        Text(
            text = "Countries",
            modifier = Modifier.padding(16.dp)
                .semantics { heading() },
            style = MaterialTheme.typography.titleMedium
        )
    }

    itemsIndexed(items) { index, item ->
        TopicItem(
            itemTitle = item.name,
            itemResId = item.icResId,
            selected = item == items[selectedCountryIndex],
            onToggle = {
                onToggle(index)
            }
        )
    }

}

private fun LazyListScope.LanguageItems(
    items: List<Language>,
    selectedLanguageIndex: Int,
    onToggle: (Int) -> Unit,
) {
    item {
        Text(
            text = "Language",
            modifier = Modifier.padding(16.dp)
                .semantics { heading() },
            style = MaterialTheme.typography.titleMedium
        )
    }

    itemsIndexed(items) { index, item ->
        TopicItem(
            itemTitle = item.name,
            itemResId = R.drawable.placeholder_1_1,
            selected = item == items[selectedLanguageIndex],
            onToggle = {
                onToggle(index)
            }
        )
    }

}


@Composable
private fun TopicItem(
    itemTitle: String,
    itemResId: Int,
    selected: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(itemResId),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
            )
            Text(
                text = itemTitle,
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.width(16.dp))
            RadioButton(
                selected = selected,
                onClick = onToggle
            )
        }
        Divider(
            modifier = modifier.padding(
                start = 72.dp, top = 8.dp, bottom = 8.dp
            ),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .1f)
        )
    }

}





