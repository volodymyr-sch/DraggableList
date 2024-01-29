-keepclassmembers class androidx.compose.foundation.lazy.LazyListState {
    private androidx.compose.foundation.lazy.LazyListScrollPosition scrollPosition;
}

-keepclassmembers class androidx.compose.foundation.lazy.LazyListScrollPosition {
    private androidx.compose.runtime.MutableIntState index$delegate;
    private java.lang.Object lastKnownFirstItemKey;
}

-keepclassmembers class androidx.compose.foundation.lazy.grid.LazyGridState {
    private androidx.compose.foundation.lazy.grid.LazyGridScrollPosition scrollPosition;
}

-keepclassmembers class androidx.compose.foundation.lazy.grid.LazyGridScrollPosition {
    private androidx.compose.runtime.MutableIntState index$delegate;
    private java.lang.Object lastKnownFirstItemKey;
}
