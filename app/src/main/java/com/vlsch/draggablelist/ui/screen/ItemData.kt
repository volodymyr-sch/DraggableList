package com.vlsch.draggablelist.ui.screen

import java.io.Serializable

data class ItemData(
    val id: String,
    val data: String,
    val isLocked: Boolean = false,
): Serializable
