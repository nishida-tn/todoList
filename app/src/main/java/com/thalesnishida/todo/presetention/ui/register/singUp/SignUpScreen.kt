package com.thalesnishida.todo.presetention.ui.register.singUp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thalesnishida.todo.navigation.Route
import com.thalesnishida.todo.navigation.SignIn
import com.thalesnishida.todo.presetention.components.ButtonDefault
import com.thalesnishida.todo.presetention.ui.base.UiEvent

@Composable
fun SignUpScreen(
    onNavigate: (Route) -> Unit = {},
    viewModel: SignUpViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }
            }
        }
    }

    if (uiState.isLoading) {
        CircularProgressIndicator()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = uiState.name,
                onValueChange = { viewModel.processIntent(SignUpIntent.ChangeName(it)) },
                label = { Text("Username") },
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.processIntent(SignUpIntent.ChangeEmail(it)) },
                label = { Text("Email") },
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.processIntent(SignUpIntent.ChangePassword(it)) },
                label = { Text("Password") },
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            ButtonDefault(
                onClick = {
                    viewModel.processIntent(SignUpIntent.SingUpConfirm)
                },
                text = "Login"
            )
            ButtonDefault(
                onClick = {
                    onNavigate(SignIn)
                },
                text = "Login"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        viewModel = hiltViewModel<SignUpViewModelImpl>()
    )
}