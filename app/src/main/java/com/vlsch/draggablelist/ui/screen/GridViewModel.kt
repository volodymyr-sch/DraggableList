package com.vlsch.draggablelist.ui.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vlsch.draggable.ext.rearrangeItems
import com.vlsch.draggablelist.ui.UiState

class GridViewModel : ViewModel() {

    private val _foo = MutableLiveData<UiState<List<ItemData>>>()
    val foo: LiveData<UiState<List<ItemData>>> = _foo

    private val _bar = MutableLiveData<UiState<List<String>>>()
    val bar: LiveData<UiState<List<String>>> = _bar

    init {
        generateWords()
    }

    private fun generateWords() {
        val fooWords = mutableListOf<ItemData>()
        val barWords = mutableListOf<String>()
        for (i in 0..150) {
            fooWords.add(ItemData(id = "id:$i","Foo $i"))
            barWords.add("Bar $i")
        }
        _foo.postValue(UiState(loading = false, data = fooWords))
        _bar.postValue(UiState(loading = false, data = barWords))
    }

    fun swapFoo(from: Int, to: Int) {
        val newList = _foo.value?.data?.toMutableList()?.rearrangeItems(from, to)
        _foo.value = UiState(loading = false, data = newList)
    }

    fun swapBar(from: Int, to: Int) {
        val newList = _bar.value?.data?.toMutableList()?.rearrangeItems(from, to)
        _bar.value = UiState(loading = false, data = newList)
    }
}
