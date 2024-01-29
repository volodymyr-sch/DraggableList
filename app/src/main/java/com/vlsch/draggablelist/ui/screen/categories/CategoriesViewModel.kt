package com.vlsch.draggablelist.ui.screen.categories

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AppSettingsAlt
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Bookmarks
import androidx.lifecycle.ViewModel
import com.vlsch.draggable.ext.rearrangeItems
import com.vlsch.draggable.library.draggable.model.ItemInfo
import com.vlsch.draggablelist.ui.screen.categories.model.ListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class CategoriesViewModel : ViewModel() {

    private val _items: MutableStateFlow<List<ListItem>> = MutableStateFlow(emptyList())
    val items: Flow<List<ListItem>> = _items

    init {
        val items = mutableListOf<ListItem>().apply {
            add(ListItem.HeaderItem("Apps", icon = Icons.Default.Apps, isLocked = false))
            add(ListItem.Item("App 1", Icons.Default.AppSettingsAlt))
            add(ListItem.Item("App 2", Icons.Default.AppSettingsAlt))
            add(ListItem.Item("App 3", Icons.Default.AppSettingsAlt))
            add(ListItem.Item("App 4", Icons.Default.AppSettingsAlt))
            add(ListItem.HeaderItem("Bookmarks", icon = Icons.Default.Bookmarks, isLocked = false))
            add(ListItem.Item("Bookmark 1", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 2", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 3", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 4", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 5", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 6", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 7", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 8", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 9", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 10", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 11", Icons.Default.Bookmark))
            add(ListItem.Item("Bookmark 12", Icons.Default.Bookmark))
        }
        _items.value = items
    }

    fun setDraggingItemNewPosition(from: ItemInfo, to: ItemInfo) {
        val newList = _items.value.toMutableList().rearrangeItems(from.index, to.index)
        _items.value = newList
    }

    fun isItemLocked(itemInfo: ItemInfo): Boolean {
        return itemInfo.index == 0
    }
}
