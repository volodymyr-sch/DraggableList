package com.vlsch.draggablelist.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vlsch.draggable.draggable
import com.vlsch.draggable.library.draggable.DraggableItem
import com.vlsch.draggable.library.draggable.rememberDraggableLazyListState

@Composable
fun LazyListScreen(
    viewModel: ListViewModel = viewModel()
) {
    Column {
        HorizontalList(viewModel)
        VerticalList(viewModel)
    }
}

@Composable
private fun HorizontalList(viewModel: ListViewModel) {
    val uiState by viewModel.birds.collectAsState(
        initial = emptyList(),
    )

    uiState.let { data ->
        Box {
            HorizontalListContent(data, viewModel)
        }
    }
}

@Composable
fun HorizontalListContent(data: List<ItemData>, viewModel: ListViewModel) {
    val lazyState = rememberDraggableLazyListState(onSwap = { from, to ->
        viewModel.swapBirds(from.index, to.index)
    }, isItemLocked = viewModel::isBirdLocked)
    LazyRow(
        state = lazyState.state,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        items(items = data, key = { item -> item.id }) { item ->
            DraggableItem(
                modifier = Modifier,
                key = item.id,
                state = lazyState,
            ) { isDragging, hoveredItem ->
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .padding(8.dp)
                        .background(color = if (item.isLocked) Color.LightGray else Color.Magenta)
                        .border(1.dp, Color.Black)
                        .draggable(lazyState, item.id)
                ) {
                    Text(
                        text = if (item.isLocked) "Locked" else item.data,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(start = 8.dp),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun VerticalList(viewModel: ListViewModel) {
    val uiState by viewModel.ducks.collectAsState(
        initial = emptyList(),
    )

    uiState.let { data ->
        Box {
            VerticalListContent(data, viewModel)
        }
    }
}

@Composable
private fun VerticalListContent(items: List<ItemData>, viewModel: ListViewModel) {
    val lazyState = rememberDraggableLazyListState(onSwap = { from, to ->
        viewModel.swapDucks(from.index, to.index)
    }, isItemLocked = viewModel::isDuckLocked)
    LazyColumn(
        state = lazyState.state,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        items(items = items, key = { item -> item.id }) { item ->
            DraggableItem(
                key = item.id,
                state = lazyState,
            ) { isDragging, _ ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(8.dp)
                        .background(color = if (item.isLocked) Color.LightGray else Color.White)
                        .border(1.dp, Color.Black)
                ) {
                    if (item.isLocked.not()) {
                        IconButton(
                            modifier = Modifier.draggable(lazyState, item.id),
                            onClick = {},
                        ) {
                            Icon(Icons.Rounded.DragHandle, contentDescription = "")
                        }
                    }
                    Text(
                        text = if (item.isLocked) "Locked" else item.data,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 8.dp),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
        }
    }
}
