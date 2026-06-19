package com.example.navegacao.telas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navegacao.data.TipoTransacao
import com.example.navegacao.data.Transacao
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.HelpCenter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: FinancasViewModel,
    onNavegarParaCadastro: (String) -> Unit
) {
    val transacoes by viewModel.transacoes.collectAsState()
    val saldo by viewModel.saldo.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Minhas Finanças") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card de Saldo
            Card(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
                    Text(text = "Saldo Atual", fontSize = 16.sp, color = Color.Gray)
                    Text(
                        text = String.format("R$ %.2f", saldo),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (saldo >= 0) Color(0xFF2E7D32) else Color(0xFFC62828)
                    )
                }
            }

            // Botões de Ação
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { onNavegarParaCadastro("RECEITA") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                ) {
                    Text("+ Receita")
                }
                Button(
                    onClick = { onNavegarParaCadastro("DESPESA") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
                    modifier = Modifier.weight(1f).padding(start = 8.dp)
                ) {
                    Text("- Despesa")
                }
            }

            // Histórico Financeiro
            Text(
                text = "Histórico Lançamentos",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(transacoes) { transacao ->
                    ItemTransacao(transacao = transacao, onDelete = { viewModel.excluirTransacao(transacao) })
                }
            }
        }
    }
}

// Adding 'coil icons' to the 'historic' (according to each category)
@Composable
fun ItemTransacao(transacao: Transacao, onDelete: () -> Unit) {
    // Map categories to appropriate icons
    val iconeCategoria = when (transacao.categoria) {
        "Alimentação" -> Icons.Default.Fastfood
        "Lazer" -> Icons.Default.ConfirmationNumber
        "Transporte" -> Icons.Default.DirectionsCar
        else -> Icons.Default.HelpCenter
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Visual Indicator: Icon with a soft background circle
                Surface(
                    shape = androidx.compose.foundation.shape.CircleShape,
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = iconeCategoria,
                        contentDescription = transacao.categoria,
                        modifier = Modifier.padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(text = transacao.descricao, fontWeight = FontWeight.Bold)
                    Text(text = transacao.categoria, fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = "$${transacao.valor}",
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = Color.Gray)
            }
        }
    }
}
