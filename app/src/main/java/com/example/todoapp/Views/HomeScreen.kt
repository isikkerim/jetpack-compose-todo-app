import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp(todoViewModel: TodoController = viewModel()) {
    val items by todoViewModel.items.collectAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ToDo App") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                TodoItemRow(item, todoViewModel::removeItem)
            }
        }

        if (showDialog) {
            AddItemDialog(onDismiss = { showDialog = false }) { description ->
                todoViewModel.addItem(description)
                showDialog = false
            }
        }
    }
}

@Composable
fun TodoItemRow(item: TodoItem, onRemove: (TodoItem) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.description, style = MaterialTheme.typography.bodyLarge)
        IconButton(onClick = { onRemove(item) }) {
            Icon(Icons.Filled.Delete, contentDescription = "Delete")
        }
    }
}

@Composable
fun AddItemDialog(onDismiss: () -> Unit, onAdd: (String) -> Unit) {
    var description by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (description.isNotBlank()) {
                    onAdd(description)
                }
            }) {
                Text("Add")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text("Add New Item") },
        text = {
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") }
            )
        }
    )
}
