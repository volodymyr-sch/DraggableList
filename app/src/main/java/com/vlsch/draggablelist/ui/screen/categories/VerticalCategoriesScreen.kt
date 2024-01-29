package com.vlsch.draggablelist.ui.screen.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.vlsch.draggablelist.ui.screen.categories.model.ListItem

@Composable
fun VerticalComplexScreen(
    categoriesViewModel: CategoriesViewModel = viewModel()
) {
    VerticalList(viewModel = categoriesViewModel)
}

@Composable
private fun VerticalList(viewModel: CategoriesViewModel) {
    val items by viewModel.items.collectAsState(
        initial = emptyList(),
    )

    if (items.isNotEmpty()) {
        Box {
            VerticalListContent(items, viewModel)
        }
    }
}

@Composable
private fun VerticalListContent(items: List<ListItem>, viewModel: CategoriesViewModel) {
    val lazyState = rememberDraggableLazyListState(onSwap = { from, to ->
        viewModel.setDraggingItemNewPosition(from, to)
    }, isItemLocked = viewModel::isItemLocked)
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
            ) { isDragging, hoveredItemKey ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(8.dp)
                        .background(color = if (hoveredItemKey == item.id && item is ListItem.Item) Color.Green else Color.White)
                        .border(1.dp, Color.Black),
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        modifier = Modifier.padding(start = if (item is ListItem.HeaderItem) 16.dp else 48.dp),
                        onClick = {},
                    ) {
                        Icon(item.icon, contentDescription = "")
                    }

                    Text(
                        text = item.id,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        ),
                    )

                    Spacer(Modifier.weight(1f).fillMaxHeight())

                    if (item is ListItem.Item) {
                        IconButton(
                            modifier = Modifier.draggable(lazyState, key = item.id),
                            onClick = {},
                        ) {
                            Icon(Icons.Rounded.DragHandle, contentDescription = "")
                        }
                    }

                }
            }
        }
    }
}
