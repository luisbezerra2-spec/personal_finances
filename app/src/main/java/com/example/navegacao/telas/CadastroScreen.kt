package com.example.navegacao.telas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.navegacao.data.TipoTransacao

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    tipo: String,
    viewModel: FinancasViewModel,
    onVoltar: () -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var valorStr by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Alimentação") }
    var dropdownExpandido by remember { mutableStateOf(false) }

    val categorias = listOf("Alimentação", "Lazer", "Transporte", "Outro")
    val tipoEnum = if (tipo == "RECEITA") TipoTransacao.RECEITA else TipoTransacao.DESPESA

    Scaffold(
        topBar = { TopAppBar(title = { Text("Adicionar $tipo") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = valorStr,
                onValueChange = { valorStr = it },
                label = { Text("Valor") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Spinner/Dropdown de Categorias
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = categoriaSelecionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            "Expandir",
                            Modifier.clickable { dropdownExpandido = true }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = dropdownExpandido,
                    onDismissRequest = { dropdownExpandido = false }
                ) {
                    categorias.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                categoriaSelecionada = cat
                                dropdownExpandido = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val valor = valorStr.toDoubleOrNull() ?: 0.0
                    if (descricao.isNotBlank() && valor > 0.0) {
                        viewModel.salvarTransacao(descricao, valor, tipoEnum, categoriaSelecionada)
                        onVoltar()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}