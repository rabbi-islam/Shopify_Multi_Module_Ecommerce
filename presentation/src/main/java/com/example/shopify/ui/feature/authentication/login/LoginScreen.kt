package com.example.shopify.ui.feature.authentication.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.shopify.R
import com.example.shopify.navigation.HomeScreen
import com.example.shopify.navigation.RegisterScreen
import com.example.shopify.ui.components.CustomSwitch
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {

    val loginState = viewModel.loginState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = loginState.value) {
            is LoginState.Success -> {
                LaunchedEffect(loginState.value) {
                    navController.navigate(HomeScreen) {
                        popUpTo(HomeScreen) {
                            inclusive = true
                        }
                    }
                }
            }

            is LoginState.Error -> {
                Text(text = state.message)
                // Show error message
            }

            is LoginState.Loading -> {
                CircularProgressIndicator()
                Text(text = "Loading...")
            }

            else -> {
                LoginContent(onSignInClicked = { email, password ->
                    viewModel.login(email, password)
                },
                    onRegisterClick = {
                        navController.navigate(RegisterScreen)
                    })
            }
        }
    }
}

@Composable
fun LoginContent(onSignInClicked: (String, String) -> Unit, onRegisterClick: () -> Unit) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        CustomSwitch(
            context = context,
            height = 40.dp,
            width = 80.dp,
            circleButtonPadding = 4.dp,
            outerBackgroundOnResource = R.drawable.switch_body_night,
            outerBackgroundOffResource = R.drawable.switch_body_day,
            circleBackgroundOnResource = R.drawable.switch_btn_moon,
            circleBackgroundOffResource = R.drawable.switch_btn_sun,
            stateOn = 1,
            stateOff = 0,
            initialValue = 0,
            onCheckedChanged = {}
        )
        Text(text = stringResource(R.string.login), style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
            },
            modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.email)) }
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.password)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                onSignInClicked(email.value, password.value)
            }, modifier = Modifier.fillMaxWidth(),
            enabled = email.value.isNotEmpty() && password.value.isNotEmpty()
        ) {
            Text(text = stringResource(id = R.string.login))
        }
        Text(text = stringResource(R.string.don_t_have_an_account_register), modifier = Modifier
            .padding(8.dp)
            .clickable {
                onRegisterClick()
            })
    }
}