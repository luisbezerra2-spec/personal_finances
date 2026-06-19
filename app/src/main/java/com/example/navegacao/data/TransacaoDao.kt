package com.example.navegacao.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransacaoDao {
    @Query("SELECT * FROM transacoes ORDER BY id DESC")
    fun listarTodas(): Flow<List<Transacao>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salvar(transacao: Transacao)

    @Delete
    suspend fun excluir(transacao: Transacao)

    @Update
    suspend fun atualizar(transacao: Transacao)
}