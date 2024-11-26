import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class TodoItem(val description: String)

class TodoController : ViewModel() {
    private val _items = MutableStateFlow<List<TodoItem>>(emptyList())
    val items: StateFlow<List<TodoItem>> = _items

    fun addItem(description: String) {
        _items.value = _items.value + TodoItem(description)
    }

    fun removeItem(item: TodoItem) {
        _items.value = _items.value - item
    }
}
