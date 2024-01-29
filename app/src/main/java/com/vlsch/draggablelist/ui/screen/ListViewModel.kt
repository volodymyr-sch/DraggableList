package com.vlsch.draggablelist.ui.screen

import androidx.lifecycle.ViewModel
import com.vlsch.draggable.ext.swap
import com.vlsch.draggable.library.draggable.model.ItemInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ListViewModel : ViewModel() {

    private val _ducks = MutableStateFlow<List<ItemData>>(listOf())
    val ducks: Flow<List<ItemData>> = _ducks

    private val _birds = MutableStateFlow<List<ItemData>>(listOf())
    val birds: Flow<List<ItemData>> = _birds

    init {
        val ducksList = mutableListOf<ItemData>()
        val birdsList = mutableListOf<ItemData>()
        for (i in 0..150) {
            ducksList.add(ItemData(id = "id:$i", "Duck$i", isLocked = i != 0 && i % 4 == 0))
            birdsList.add(ItemData(id = "id:$i", "Bird$i", isLocked = i != 0 && i % 3 == 0))
        }
        _ducks.value = ducksList
        _birds.value = birdsList
    }

    fun swapDucks(from: Int, to: Int) {
        _ducks.value = _ducks.value.toMutableList().swap(from, to)
    }

    fun swapBirds(from: Int, to: Int) {
        _birds.value = _birds.value.toMutableList().swap(from, to)
    }

    fun isDuckLocked(itemInfo: ItemInfo): Boolean {
        return _ducks.value[itemInfo.index].isLocked
    }

    fun isBirdLocked(itemInfo: ItemInfo): Boolean {
        return _birds.value[itemInfo.index].isLocked
    }
}
