package com.vlsch.draggablelist.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlsch.draggable.library.draggable.DraggableItem
import com.vlsch.draggable.library.draggable.rememberDraggableLazyGridState
import com.vlsch.draggable.longPressDraggable
import com.vlsch.draggablelist.ui.ProgressContent
import com.vlsch.draggablelist.ui.UiState
import com.vlsch.draggablelist.ui.theme.Cyan


@Composable
fun GridListScreen(
    viewModel: GridViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalGrid(viewModel)
        VerticalGrid(viewModel)
    }
}

@Composable
private fun HorizontalGrid(viewModel: GridViewModel) {
    val state: UiState<List<ItemData>> by viewModel.foo.observeAsState(
        initial = UiState(loading = true, data = listOf())
    )
    when {
        state.isInitialLoading() -> ProgressContent()
        state.hasError() -> Text(text = "Error")
        !state.data.isNullOrEmpty() -> HorizontalContent(
            fooWords = state.data!!,
            viewModel = viewModel
        )
    }
}

@Composable
private fun HorizontalContent(
    fooWords: List<ItemData>,
    viewModel: GridViewModel,
) {
    val lazyState = rememberDraggableLazyGridState(onSwap = { from, to ->
        viewModel.swapFoo(from.index, to.index)
    })
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(80.dp),
        state = lazyState.gridState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .background(color = Color.LightGray)
            .height(280.dp)

    ) {
        items(items = fooWords, key = ItemData::id) { item ->
            DraggableItem(
                key = item.id,
                state = lazyState,
                modifier = Modifier,
                content = { isDragging, hoveredItem ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(color = if (hoveredItem == item.id) Color.Magenta else Color.Blue)
                            .size(80.dp)
                            .longPressDraggable(
                                draggableState = lazyState, key = item.id
                            )
                    ) {
                        Text(
                            text = item.data,
                            modifier = Modifier.align(Alignment.Center),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }
            )
        }
    }
}


@Composable
private fun VerticalGrid(
    viewModel: GridViewModel
) {
    val state: UiState<List<String>> by viewModel.bar.observeAsState(
        initial = UiState(loading = true, data = listOf())
    )

    when {
        state.isInitialLoading() -> ProgressContent()
        state.hasError() -> Text(text = "Error")
        !state.data.isNullOrEmpty() -> VerticalGridContent(
            data = state.data!!,
            viewModel
        )
    }
}

@Composable
private fun VerticalGridContent(
    data: List<String>,
    viewModel: GridViewModel,
) {
    val lazyState = rememberDraggableLazyGridState(onSwap = { from, to ->
        viewModel.swapBar(from.index, to.index)
    })
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        state = lazyState.gridState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .background(color = Color.LightGray)
            .height(280.dp)
    ) {
        items(items = data, key = { it }) { item ->
            DraggableItem(
                key = item,
                state = lazyState,
                content = { isDragging, _ ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .background(color = Cyan)
                            .size(120.dp)
                            .longPressDraggable(
                                draggableState = lazyState, key = item
                            )
                    ) {
                        Text(
                            text = item,
                            modifier = Modifier.align(Alignment.Center),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            ),
                        )
                    }
                }
            )
        }
    }
}
