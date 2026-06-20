package com.example.navegacao.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navegacao.telas.CadastroScreen
import com.example.navegacao.telas.FinancasViewModel
import com.example.navegacao.telas.HomeScreen

@Composable
fun SetupNavigation() {
    val navController = rememberNavController()
    val viewModel: FinancasViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onNavegarParaCadastro = { tipo ->
                    navController.navigate("cadastro/$tipo/-1")
                },
                onNavegarParaEditar = { transacao ->
                    navController.navigate("cadastro/${transacao.tipo.name}/${transacao.id}")
                }
            )
        }

        // criar e editar
        composable("cadastro/{tipo}/{transacaoId}") { backStackEntry ->
            val tipo = backStackEntry.arguments?.getString("tipo") ?: "RECEITA"
            val transacaoId = backStackEntry.arguments?.getString("transacaoId")?.toLongOrNull() ?: -1L

            CadastroScreen(
                tipo = tipo,
                transacaoId = transacaoId,
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() }
            )
        }
    }
}