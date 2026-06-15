package com.example.navegacao.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Produto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val preco: String,
    val imagemId: String,
    val descricao: String
)



/*
data class Produto(
    val nome: String,
    val preco: String,
    val imagemId: Any,
    val descricao: String
)

 */