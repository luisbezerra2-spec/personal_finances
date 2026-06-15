package com.example.navegacao.telas

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.navegacao.data.Produto
import com.example.navegacao.navigation.Telas

@Composable
fun CardProduto(
    produto: Produto,
    onVerDetalhes: () -> Unit,
    onEditarClick: () -> Unit,
    onDeletarClick: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = produto.imagemId,
            contentDescription = "Imagem de ${produto.nome}",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = produto.nome, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = produto.preco, fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onVerDetalhes) {
                Text("Ver Detalhes")
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEditarClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar produto",
                        tint = Color.Gray
                    )
                }

                IconButton(onClick = onDeletarClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar produto",
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                }
            }
        }


    }
}

@Composable
fun TelaProduto(
    backStack: SnapshotStateList<Telas>,
    listaDeProdutos: SnapshotStateList<Produto>,
    onEditarClick: (Produto) -> Unit,
    onDeletarClick: (Produto) -> Unit
){

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "LUME",
            fontSize = 25.sp
        )


        LazyColumn(
            modifier = Modifier
                .weight(1f),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(listaDeProdutos){ item ->
                CardProduto(
                    produto = item,
                    onVerDetalhes = {
                        backStack.add(Telas.TelaDetalhes(item))
                    },
                    onEditarClick = {onEditarClick(item)},
                    onDeletarClick = {onDeletarClick(item)}
                )
            }
        }

        // Dica: Adicione um botão para ir para a Tela de Cadastro aqui!
        Button(
            onClick = { backStack.add(Telas.TelaCadastro()) }, modifier = Modifier.width(200.dp).height(50.dp)) {
            Text("Novo Produto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(backStack.isNotEmpty()){
                    backStack.removeAt(backStack.size - 1)
                }
            },
            modifier = Modifier.width(200.dp).height(50.dp)
        ){
            Text(text = "Voltar para Home")
        }
    }
}



