package com.vlsch.draggable.library.draggable

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.vlsch.draggable.library.draggable.hijacker.rememberLazyGridStateHijacker
import com.vlsch.draggable.library.draggable.model.ItemInfo
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberDraggableLazyGridState(
    state: LazyGridState = rememberLazyGridState(),
    speedFactor: Float = 1f,
    autoscroller: Autoscroller? = null,
    onSwap: (ItemInfo, ItemInfo) -> Unit,
    isItemLocked: ((itemToSwap: ItemInfo) -> Boolean)? = null,
): DraggableLazyGridState {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    rememberLazyGridStateHijacker(gridState = state, enabled = true)
    val draggableState = remember(state) {
        DraggableLazyGridState(
            state,
            speedFactor,
            coroutineScope,
            autoscroller,
            onSwap,
            isItemLocked,
        )
    }

    return draggableState
}

class DraggableLazyGridState(
    val gridState: LazyGridState,
    speedFactor: Float,
    coroutineScope: CoroutineScope,
    autoscroller: Autoscroller?,
    onSwap: (ItemInfo, ItemInfo) -> Unit,
    isItemLocked: ((itemToSwap: ItemInfo) -> Boolean)?,
) : DraggableState<LazyGridItemInfo>(
    coroutineScope,
    onSwap,
    autoscroller,
    speedFactor,
    isItemLocked,
) {

    override val isGrid: Boolean
        get() = true
    override val isVertical: Boolean
        get() = gridState.layoutInfo.orientation == Orientation.Vertical
    override val viewportStartOffset: Int
        get() = gridState.layoutInfo.viewportStartOffset
    override val viewportEndOffset: Int
        get() = gridState.layoutInfo.viewportEndOffset
    override val firstVisibleItemScrollOffset: Int
        get() = gridState.firstVisibleItemScrollOffset
    override val firstVisibleItemIndex: Int
        get() = gridState.firstVisibleItemIndex
    override val visibleItemsInfo: List<LazyGridItemInfo>
        get() = gridState.layoutInfo.visibleItemsInfo
    override val LazyGridItemInfo.itemIndex: Int
        get() = index
    override val LazyGridItemInfo.itemKey: Any
        get() = key
    override val LazyGridItemInfo.left: Int
        get() = offset.x
    override val LazyGridItemInfo.right: Int
        get() = offset.x + size.width
    override val LazyGridItemInfo.top: Int
        get() = offset.y
    override val LazyGridItemInfo.bottom: Int
        get() = offset.y + size.height
    override val LazyGridItemInfo.height: Int
        get() = size.height
    override val LazyGridItemInfo.width: Int
        get() = size.width
    override val layoutMode: LayoutMode
        get() = LayoutMode.GRID

    override suspend fun scrollBy(distance: Float) {
        gridState.scrollBy(distance)
    }

    override suspend fun scrollToItem(index: Int, scrollOffset: Int) {
        gridState.scrollToItem(index, scrollOffset)
    }
}
