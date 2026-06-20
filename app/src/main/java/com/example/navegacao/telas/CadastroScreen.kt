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
    transacaoId: Long,
    viewModel: FinancasViewModel,
    onVoltar: () -> Unit
) {
    var descricao by remember { mutableStateOf("") }
    var valorStr by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf("Alimentação") }
    var dropdownExpandido by remember { mutableStateOf(false) }

    val categorias = listOf("Alimentação", "Lazer", "Transporte", "Outro")
    val tipoEnum = if (tipo == "RECEITA") TipoTransacao.RECEITA else TipoTransacao.DESPESA

    // Define o título da tela baseado na ação (Adicionar ou Editar)
    val tituloTela = if (transacaoId == -1L) "Adicionar $tipo" else "Editar $tipo"

    // Disparado ao abrir a tela: se transacaoId for válido (diferente de -1), busca no banco e preenche os campos
    LaunchedEffect(transacaoId) {
        if (transacaoId != -1L) {
            val transacao = viewModel.buscarPorId(transacaoId)
            if (transacao != null) {
                descricao = transacao.descricao
                valorStr = transacao.valor.toString()
                categoriaSelecionada = transacao.categoria
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(tituloTela) }) }
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

            // Categorias
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

            // Botão Principal de Ação (Salvar ou Atualizar)
            Button(
                onClick = {
                    val valor = valorStr.toDoubleOrNull() ?: 0.0
                    if (descricao.isNotBlank() && valor > 0.0) {
                        if (transacaoId == -1L) {
                            // Cria um novo registro se o ID for -1
                            viewModel.salvarTransacao(descricao, valor, tipoEnum, categoriaSelecionada)
                        } else {
                            // Atualiza o existente se já possuir um ID válido
                            viewModel.atualizarTransacao(transacaoId, descricao, valor, tipoEnum, categoriaSelecionada)
                        }
                        onVoltar()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (transacaoId == -1L) "Salvar" else "Atualizar Alterações")
            }

            // Botão Voltar (Cancela a operação atual)
            OutlinedButton(
                onClick = { onVoltar() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Voltar")
            }
        }
    }
}