package com.thalesnishida.todo.presetention.ui.register.signIn

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thalesnishida.todo.R
import com.thalesnishida.todo.navigation.Onboarding
import com.thalesnishida.todo.navigation.Route
import com.thalesnishida.todo.presetention.ui.base.UiEvent
import com.thalesnishida.todo.presetention.ui.theme.TodoTheme

@Composable
fun SignInScreen(
    onNavigate: (route: Route) -> Unit = {},
    viewModel: SignInViewModel,
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

    SignInScreen(
        uiState = uiState,
        onEmailChange = { viewModel.processIntent(SignInIntent.ChangeEmail(it)) },
        onPasswordChange = { viewModel.processIntent(SignInIntent.ChangePassword(it)) },
        onSignIn = { viewModel.processIntent(SignInIntent.SignIn) },
        onNavigateUp = { onNavigate(Onboarding) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    uiState: SignInUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignIn: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = onSignIn,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Divider(modifier = Modifier.weight(1f))
                    Text(
                        text = "or",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Divider(modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(32.dp))
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_google_logo),
                        contentDescription = "Google"
                    )
                    Text(
                        text = "Login with Google",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_apple_logo),
                        contentDescription = "Apple"
                    )
                    Text(
                        text = "Login with Apple",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Don't have an account?")
                    TextButton(onClick = { /*TODO*/ }) {
                        Text("Register")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenPreview() {
    TodoTheme(darkTheme = true) {
        SignInScreen(
            uiState = SignInUiState(
                email = "Mahsa Esfehany",
                password = "password"
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onSignIn = {},
            onNavigateUp = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInScreenLoadingPreview() {
    TodoTheme {
        SignInScreen(
            uiState = SignInUiState(
                isLoading = true
            ),
            onEmailChange = {},
            onPasswordChange = {},
            onSignIn = {},
            onNavigateUp = {}
        )
    }
}
