package br.edu.ifsp.scl.prdm.sc3047784.intentscompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController,
    onAddWord: (String) -> Unit
) {
    var currentString by rememberSaveable {
        mutableStateOf("")
    }

    val word by navController
        .currentBackStackEntry!!
        .savedStateHandle
        .getStateFlow<String?>("word", null)
        .collectAsStateWithLifecycle()

    LaunchedEffect(word) {

        if (!word.isNullOrBlank()) {

            currentString =
                if (currentString.isEmpty()) {
                    word!!
                } else {
                    "$currentString $word"
                }

            navController
                .currentBackStackEntry
                ?.savedStateHandle
                ?.set("word", null)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "String atual"
        )

        OutlinedTextField(
            value = currentString,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = {
                onAddWord(currentString)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Adicionar palavra")
        }

        TextButton(
            onClick = {
                currentString = ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text("Reiniciar")
        }
    }
}
