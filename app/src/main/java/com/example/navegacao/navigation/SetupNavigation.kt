package com.example.navegacao.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.navegacao.data.Produto
import com.example.navegacao.telas.ProdutoViewModel
import com.example.navegacao.telas.TelaCadastro
import com.example.navegacao.telas.TelaDetalhes
import com.example.navegacao.telas.TelaHome
import com.example.navegacao.telas.TelaProduto

@Composable
fun SetupNavigation(viewModel: ProdutoViewModel){
    val backStack = rememberSaveable { mutableStateListOf<Telas>(Telas.TelaHome) }

    val produtos by viewModel.listaDeProdutosState.collectAsState()

    NavDisplay(
        backStack = backStack,
        onBack = {backStack.removeLastOrNull()},
        entryProvider = entryProvider {

            entry<Telas.TelaHome> {
                TelaHome(backStack = backStack)
            }

            entry <Telas.TelaProduto> {
                val snapshotList = remember(produtos) {
                    mutableStateListOf<Produto>().apply { addAll(produtos) }
                }
                TelaProduto(
                    backStack = backStack,
                    listaDeProdutos = snapshotList,
                    onDeletarClick = {produtos ->
                        viewModel.deletarProduto(produtos)
                    },
                    onEditarClick = { produtos ->
                        backStack.add(Telas.TelaCadastro(produtoEdicao = produtos))
                    }
                )
            }

            entry <Telas.TelaCadastro>{ backStackEntry ->

                val produtoParaEditar = backStackEntry.produtoEdicao

                TelaCadastro(backStack,
                    produtoEdicao = produtoParaEditar,
                    onSalvarClick = { novoProduto ->
                        if (produtoParaEditar == null) {
                            viewModel.adicionarProduto(novoProduto)
                        } else {
                            viewModel.atualizarProduto(novoProduto)
                        }
                    }
                )
            }

            entry <Telas.TelaDetalhes> { it ->
                TelaDetalhes(
                    produto = it.produto,
                    backStack = backStack
                )
            }

        }
    )
}


















