package com.example.navegacao.telas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navegacao.data.Produto
import com.example.navegacao.data.ProdutoDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProdutoViewModel(private val produtoDao: ProdutoDao) : ViewModel(){

    val listaDeProdutosState = produtoDao.listarTodos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun adicionarProduto(produto: Produto){
        viewModelScope.launch {
            produtoDao.inserir(produto)
        }
    }

    fun atualizarProduto(produto: Produto){
        viewModelScope.launch {
            produtoDao.atualizar(produto)
        }
    }

    fun deletarProduto(produto: Produto){
        viewModelScope.launch {
            produtoDao.deletar(produto)
        }
    }
}