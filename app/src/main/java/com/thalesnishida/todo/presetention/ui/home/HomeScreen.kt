package com.thalesnishida.todo.presetention.ui.home

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thalesnishida.todo.R
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.presetention.ui.home.components.HomeDialogManager
import com.thalesnishida.todo.presetention.ui.home.components.TodoItem
import com.thalesnishida.todo.presetention.ui.home.components.UserAvatar
import com.thalesnishida.todo.presetention.ui.theme.TodoTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreateCategory: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Permissão necessária para lembretes", Toast.LENGTH_LONG)
                    .show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is HomeSideEffect.ScrollToTop -> {
                    Toast.makeText(context, "Rolando para o topo!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    HomeScreenContent(
        uiState = uiState,
        onAddTodoClick = { viewModel.onAddTodoClick() },
        onToggleStatus = { id, isCompleted ->
            viewModel.processIntent(HomeIntent.ToggleTodoStatus(id, isCompleted))
        },
        onTodoClick = { id -> viewModel.openDialog(id) },
        onDismissDialog = { viewModel.closeDialog() },
        onSaveEdit = { id, title, desc ->
            viewModel.processIntent(HomeIntent.UpdateTodo(id, title, desc))
        },
        onAddTodo = { title, description, _, category, priority ->
            viewModel.processIntent(
                HomeIntent.AddTodo(
                    title,
                    description,
                    uiState.draftScheduledTimestamp,
                    category,
                    priority
                )
            )
            viewModel.processIntent(HomeIntent.ClearDraftTime)
        },
        onDateTimeConfirmed = { date, hour, minute ->
            viewModel.processIntent(HomeIntent.UpdateDraftTime(date, hour, minute))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeState,
    onAddTodoClick: () -> Unit,
    onToggleStatus: (String, Boolean) -> Unit,
    onTodoClick: (String) -> Unit,
    onDismissDialog: () -> Unit,
    onSaveEdit: (String, String, String) -> Unit,
    onAddTodo: (String, String, Long, String?, Int?) -> Unit,
    onDateTimeConfirmed: (Long, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.size(24.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(modifier = Modifier.width(18.dp).height(2.dp).background(Color.White))
                    Box(modifier = Modifier.width(24.dp).height(2.dp).background(Color.White))
                    Box(modifier = Modifier.width(12.dp).height(2.dp).background(Color.White))
                }

                Text(
                    text = stringResource(R.string.nav_index),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 20.sp
                    ),
                    color = Color.White
                )

                UserAvatar(photoUri = null, size = 42.dp)
            }
        },
        bottomBar = {
            HomeBottomBar(onAddTodoClick = onAddTodoClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var searchTask by remember { mutableStateOf("") }
            OutlinedTextField(
                value = searchTask,
                onValueChange = { searchTask = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_placeholder),
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                shape = RoundedCornerShape(4.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF979797),
                    unfocusedBorderColor = Color(0xFF979797),
                    focusedContainerColor = Color(0xFF1D1D1D),
                    unfocusedContainerColor = Color(0xFF1D1D1D)
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Surface(
                    color = Color(0xFF1D1D1D).copy(alpha = 0.5f),
                    shape = RoundedCornerShape(4.dp),
                    onClick = {}
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.filter_today),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (val dataState = uiState.listTodoState) {
                is ListTodoState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }

                is ListTodoState.Error -> {
                    Text(
                        text = "Erro: ${dataState.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is ListTodoState.Success -> {
                    if (dataState.todos.isEmpty()) {
                        Spacer(modifier = Modifier.weight(1f))
                        Image(
                            painter = painterResource(R.drawable.ic_checklist),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )
                        Text(
                            text = stringResource(R.string.task_not_found),
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.weight(1.5f))
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(dataState.todos, key = { it.id }) { todo ->
                                TodoItem(
                                    todo = todo,
                                    onToggleStatus = onToggleStatus,
                                    onClick = onTodoClick,
                                    isSelected = todo.id == dataState.todos.firstOrNull()?.id
                                )
                            }
                            
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Surface(
                                        color = Color(0xFF1D1D1D).copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(4.dp),
                                        onClick = {}
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = stringResource(R.string.filter_completed),
                                                color = Color.White,
                                                fontSize = 14.sp
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Icon(
                                                imageVector = Icons.Default.KeyboardArrowDown,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(80.dp))
                            }
                        }
                    }
                }
            }
        }
    }

    val currentTodos = (uiState.listTodoState as? ListTodoState.Success)?.todos.orEmpty()

    HomeDialogManager(
        dialogState = uiState.activeDialog,
        todos = currentTodos,
        onDismiss = onDismissDialog,
        onSaveEdit = onSaveEdit,
        onAddTodo = onAddTodo,
        draftScheduledTimestamp = uiState.draftScheduledTimestamp,
        onDateTimeConfirmed = onDateTimeConfirmed,
    )
}

@Composable
fun HomeBottomBar(
    onAddTodoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavigationBar(
            modifier = Modifier.height(80.dp),
            containerColor = Color(0xFF363636),
            tonalElevation = 0.dp
        ) {
            NavigationBarItem(
                selected = true,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_home),
                        contentDescription = stringResource(R.string.nav_index),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(R.string.nav_index), fontSize = 12.sp) }
            )
            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_timer),
                        contentDescription = stringResource(R.string.nav_calendar),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(R.string.nav_calendar), fontSize = 12.sp) }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_check),
                        contentDescription = stringResource(R.string.nav_focuse),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(R.string.nav_focuse), fontSize = 12.sp) }
            )
            NavigationBarItem(
                selected = false,
                onClick = {},
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.avatar_placeholder),
                        contentDescription = stringResource(R.string.nav_profile),
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(stringResource(R.string.nav_profile), fontSize = 12.sp) }
            )
        }
        
        FloatingActionButton(
            onClick = onAddTodoClick,
            modifier = Modifier
                .padding(bottom = 40.dp)
                .size(64.dp)
                .clip(CircleShape),
            containerColor = Color(0xFF8687E7),
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.add_task),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    TodoTheme {
        val sampleTodos = listOf(
            Todo(
                id = "1",
                title = "Do Math Homework",
                description = "University assignment",
                isCompleted = false,
                createdAt = "2024-06-01T10:00:00Z",
                scheduler = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 16)
                    set(Calendar.MINUTE, 45)
                }.timeInMillis,
                priority = 1,
                category = "University",
                categoryBackgroundColor = null,
                categoryColor = null,
                categoryIcon = null,
            ),
            Todo(
                id = "2",
                title = "Tack out dogs",
                description = "Take them to the park",
                isCompleted = false,
                createdAt = "2024-06-01T11:00:00Z",
                scheduler = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 18)
                    set(Calendar.MINUTE, 20)
                }.timeInMillis,
                priority = 2,
                category = "Home",
                categoryBackgroundColor = null,
                categoryColor = null,
                categoryIcon = null,
            ),
            Todo(
                id = "3",
                title = "Business meeting with CEO",
                description = "Discuss strategy",
                isCompleted = false,
                createdAt = "2024-06-01T11:00:00Z",
                scheduler = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, 8)
                    set(Calendar.MINUTE, 15)
                }.timeInMillis,
                priority = 3,
                category = "Work",
                categoryBackgroundColor = null,
                categoryColor = null,
                categoryIcon = null,
            )
        )
        HomeScreenContent(
            uiState = HomeState(
                listTodoState = ListTodoState.Success(sampleTodos)
            ),
            onAddTodoClick = {},
            onToggleStatus = { _, _ -> },
            onTodoClick = {},
            onDismissDialog = {},
            onSaveEdit = { _, _, _ -> },
            onAddTodo = { _, _, _, _, _ -> },
            onDateTimeConfirmed = { _, _, _ -> }
        )
    }
}
