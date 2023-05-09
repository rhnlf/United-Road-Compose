package com.rhnlf.unitedroadcompose.ui.screen.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rhnlf.unitedroadcompose.di.Injection
import com.rhnlf.unitedroadcompose.model.Player
import com.rhnlf.unitedroadcompose.ui.ViewModelFactory
import com.rhnlf.unitedroadcompose.ui.common.UiState
import com.rhnlf.unitedroadcompose.ui.component.PlayerItem
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
    modifier: Modifier = Modifier,
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel
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
                    NotFound()
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
                .padding(bottom = 30.dp)
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

@Composable
fun NotFound() {
    Text(
        text = "Player not found",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit, modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(elevation = 10.dp, shape = CircleShape)
            .clip(shape = CircleShape)
            .size(56.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White, contentColor = MaterialTheme.colors.primary
        )
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = null,
        )
    }
}