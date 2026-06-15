
package com.example.navegacao.navigation

import com.example.navegacao.data.Produto

sealed class Telas {

    data object TelaHome: Telas()
    data object TelaProduto: Telas()

    data class TelaDetalhes(val produto: Produto) : Telas()

    data class TelaCadastro(val produtoEdicao: Produto? = null) : Telas()
}


