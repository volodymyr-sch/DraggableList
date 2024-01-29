# DraggableList

DraggableList is a lightweight Android library designed to seamlessly integrate drag & drop functionality into Jetpack Compose's LazyList and LazyGrid. Also, no animation bug with the first item in the list!

<p float="left">
  <img src="https://github.com/volodymyr-sch/DraggableList/blob/master/assets/lazy_list.gif?raw=true" width="300" />
  <img src="https://github.com/volodymyr-sch/DraggableList/blob/master/assets/lazy_grid.gif?raw=true" width="300" />
</p>

## Installation

Add the following dependency to your build.gradle file:
```kotlin
dependencies {
    implementation("com.vlsch:draggable:1.0.0")
}
```

## Usage

- Create a `rememberDraggableLazyListState` for LazyList or a `rememberDraggableLazyGridState` for LazyGrid.
```kotlin
val lazyState = rememberDraggableLazyListState(onSwap = { from, to ->
    viewModel.swapItems(from.index, to.index)
}, isItemLocked = viewModel::isItemLocked)
```
- Pass the layout state to the LazyList/LazyGrid.
- ***Provide a key for each item.*** The library requires a unique key for each item to function properly.
- Inside the `items` block, wrap your content with a `DraggableItem` composable, and pass the key or the item index to it as well. The `DraggableItem` provides `isDragging` and `hoveredItemKey` variables, which represent the dragging state and the key of the item that is about to be swapped, respectively. These variables can be used for additional UI modifications.
- Apply the `draggable` or `longPressDraggable` modifier to the content you want to serve as the drag anchor (this can be the root composable or any other composable inside the `DraggableItem`). Pass the item's key to the draggable modifier to ensure proper tracking and functionality.
```kotlin
LazyColumn(state = lazyState.state) {
    items(items = viewModel.items, key = { item -> item.id }) { item ->
        DraggableItem(
            key = item.id,
            state = lazyState,
        ) { isDragging, hoveredItemKey ->
            // Your content
            Row(
                modifier = Modifier
                    //If item is hovered by the dragged item, highlight it by changing the background color 
                    .background(color = if (hoveredItemKey == item.id && item is ListItem.Item) Color.Green else Color.White),
            ) {
                IconButton(
                    //Makes the item draggable by pressing on the icon
                    modifier = Modifier.draggable(lazyState, key = item.id),
                    onClick = {},
                ) {
                    Icon(Icons.Rounded.DragHandle, contentDescription = "")
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
            }
        }
    }
}
```
- If you want to lock some items, you can pass a `isItemLocked` lambda to the `rememberDraggableLazyListState` or `rememberDraggableLazyGridState` composable. The lambda should return true if the item should be locked, and false otherwise. The locked items cannot be dragged or swapped.
```kotlin
val lazyState = rememberDraggableLazyListState(onSwap = { from, to ->
    viewModel.swapItems(from.index, to.index)
}, isItemLocked = viewModel::isItemLocked)

class ViewModel {
    fun isItemLocked(item: ListItem): Boolean {
        //For example, lock all headers
        return item is ListItem.Header
    }
}
```
- The Autoscroller interface is used to handle the automatic scrolling behavior when a user drags an item to the edge of the viewport. By default, the library uses the DefaultAutoscroller class, which is a concrete implementation of the Autoscroller interface. However, if you want to customize the autoscrolling behavior, you can create your own class that extends the Autoscroller interface and override the start method.
```kotlin
class CustomAutoscroller : Autoscroller {
    override suspend fun start(
        dragDirection: DragDirection,
        overscroll: Float,
        scrollBy: suspend (Float) -> Unit,
    ) {
        // Your custom autoscrolling logic goes here, you can check the DefaultAutoscroller class for reference
    }
}
```

## Proguard

The library is using reflection to get rid of the bug with animation of the first item in the list. If you have any crashes in release build related to this, add the following rules to your proguard-rules.pro file:
```
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
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
