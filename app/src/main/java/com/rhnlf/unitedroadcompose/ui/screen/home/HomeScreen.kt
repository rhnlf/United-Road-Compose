package com.rhnlf.unitedroadcompose.ui.screen.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rhnlf.unitedroadcompose.di.Injection
import com.rhnlf.unitedroadcompose.model.Player
import com.rhnlf.unitedroadcompose.model.PlayersData
import com.rhnlf.unitedroadcompose.ui.ViewModelFactory
import com.rhnlf.unitedroadcompose.ui.common.UiState
import com.rhnlf.unitedroadcompose.ui.component.PlayerItem
import com.rhnlf.unitedroadcompose.ui.component.PlayerNotFound
import com.rhnlf.unitedroadcompose.ui.component.ScrollToTopButton
import com.rhnlf.unitedroadcompose.ui.component.SearchBar
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateToDetail: (Int) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllPlayers()
            }

            is UiState.Success -> {
                HomeContent(
                    player = uiState.data,
                    navigateToDetail = navigateToDetail,
                    viewModel = viewModel,
                    modifier = modifier,
                )
            }

            is UiState.Error -> {
            }
        }
    }
}

@Composable
fun HomeContent(
    player: List<Player>,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier,
) {
    val query by viewModel.query

    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(top = 92.dp, bottom = 84.dp),
        ) {
            if (player.isEmpty()) {
                item {
                    PlayerNotFound()
                }
            }
            items(player, key = { it.id }) { data ->
                PlayerItem(
                    name = data.name,
                    position = data.position,
                    image = data.image,
                    modifier = Modifier.clickable {
                        navigateToDetail(data.id)
                    },
                )
            }
        }
        SearchBar(
            query = query,
            onQueryChange = viewModel::search,
            modifier = Modifier.background(MaterialTheme.colors.primary)
        )
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(onClick = {
                scope.launch {
                    listState.animateScrollToItem(index = 0)
                }
            })
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun HomeScreenPreview() {
    HomeContent(
        PlayersData.players,
        navigateToDetail = {},
        viewModel = HomeViewModel(Injection.provideRepository()),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    )
}