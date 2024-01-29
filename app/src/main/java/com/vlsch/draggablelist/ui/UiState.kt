package com.vlsch.draggablelist.ui

data class UiState<T>(
    val loading: Boolean = false,
    val error: Throwable? = null,
    val data: T? = null
) {

    fun isInitialLoading(): Boolean {
        return loading && error == null && data == null
    }

    fun hasError(): Boolean {
        return error != null
    }
}
