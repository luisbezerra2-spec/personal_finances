package com.example.navegacao.telas

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.navegacao.data.AppDatabase
import com.example.navegacao.data.TipoTransacao
import com.example.navegacao.data.Transacao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FinancasViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).transacaoDao()

    val transacoes = dao.listarTodas().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val saldo = transacoes.map { lista ->
        lista.sumOf { if (it.tipo == TipoTransacao.RECEITA) it.valor else -it.valor }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )

    fun salvarTransacao(descricao: String, valor: Double, tipo: TipoTransacao, categoria: String) {
        viewModelScope.launch {
            dao.salvar(Transacao(descricao = descricao, valor = valor, tipo = tipo, categoria = categoria))
        }
    }

    fun excluirTransacao(transacao: Transacao) {
        viewModelScope.launch {
            dao.excluir(transacao)
        }
    }

    fun atualizarTransacao(id: Long, descricao: String, valor: Double, tipo: TipoTransacao, categoria: String) {
        viewModelScope.launch {
            dao.atualizar(
                Transacao(id = id, descricao = descricao, valor = valor, tipo = tipo, categoria = categoria)
            )
        }
    }

    // Helper to fetch a transaction if we need to pre-fill the screen
    suspend fun buscarPorId(id: Long): Transacao? {
        return dao.buscarPorId(id)
    }
}