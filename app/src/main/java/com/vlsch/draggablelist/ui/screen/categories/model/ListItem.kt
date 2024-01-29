package com.vlsch.draggablelist.ui.screen.categories.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
sealed interface ListItem {
    val id: String
    val isLocked: Boolean
    val icon: ImageVector

    @Immutable
    data class HeaderItem(
        override val id: String,
        override val icon: ImageVector,
        override val isLocked: Boolean = false,
    ) : ListItem

    @Immutable
    data class Item(
        override val id: String,
        override val icon: ImageVector,
        override val isLocked: Boolean = false,
    ) : ListItem
}
