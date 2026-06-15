package com.example.navegacao.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao{
    @Query("SELECT * FROM produto")
    fun listarTodos(): Flow<List<Produto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(produto: Produto)

    @Delete
    suspend fun deletar(produto: Produto)

    @Update
    suspend fun atualizar(produto: Produto)
}

