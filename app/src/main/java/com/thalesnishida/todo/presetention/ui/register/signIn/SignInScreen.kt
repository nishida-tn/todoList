package com.thalesnishida.todo.presetention.ui.register.signIn

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thalesnishida.todo.navigation.Route
import com.thalesnishida.todo.presetention.ui.base.UiEvent

@Composable
fun SignInScreen(
    onNavigate: (route: Route) -> Unit = {},
    viewModel: SignInViewModel
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onNavigate(event.route)
                }

                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoading) {
            Box(

            ) {
                CircularProgressIndicator()
            }
        } else {
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.processIntent(SignInIntent.ChangeEmail(it)) },
                label = { Text("Email") },
                maxLines = 1
            )

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { viewModel.processIntent(SignInIntent.ChangePassword(it)) },
                label = { Text("Password") },
                maxLines = 1
            )

            Button(
                onClick = {
                    viewModel.processIntent(SignInIntent.SignIn)
                }
            ) { Text("Sign In") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(){
    SignInScreen(
        onNavigate = {},
        viewModel = hiltViewModel<SignInViewModelMock>()
    )
}