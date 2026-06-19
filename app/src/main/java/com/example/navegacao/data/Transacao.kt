package com.example.navegacao.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TipoTransacao { RECEITA, DESPESA }

@Entity(tableName = "transacoes")
data class Transacao(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val descricao: String,
    val valor: Double,
    val tipo: TipoTransacao,
    val categoria: String
)