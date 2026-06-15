package com.example.navegacao.telas

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.navegacao.data.Produto
import com.example.navegacao.navigation.Telas
import com.example.navegacao.ui.theme.DarkBeige
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(
    backStack: SnapshotStateList<Telas>,
    produtoEdicao: Produto? = null,
    onSalvarClick: (Produto) -> Unit
){
    // Estados para capturar o que o usuário digita
    var nome by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var imagemCaminhoInterno by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    LaunchedEffect(produtoEdicao) {
        produtoEdicao?.let { produto ->
            nome = produto.nome
            preco = produto.preco.replace("R$ ", "")
            descricao = produto.descricao
            imagemCaminhoInterno = produto.imagemId
            imageUri = Uri.fromFile(File(produto.imagemId))
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {uri: Uri? ->
        uri?.let {
            imageUri = it

            val caminhoSalvo = copiaImagem(context, it)
            if (caminhoSalvo != null){
                imagemCaminhoInterno = caminhoSalvo
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = if (produtoEdicao == null) "Cadastrar novo produto" else "Editar Produto", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBeige)
            )
        }
    ) {padding ->
        Column (
            modifier = Modifier.fillMaxSize().padding(top = padding.calculateTopPadding()).padding(horizontal = 16.dp, vertical = 24.dp).verticalScroll(
                rememberScrollState()
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = {nome = it}, label = {Text("Nome do Produto")}, modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = preco,
                onValueChange = {preco = it}, label = {Text("Preço (ex: 99.90)")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = descricao,
                onValueChange = {descricao = it}, label = {Text("Insira a descrição do produto")},
                modifier = Modifier.fillMaxWidth(), minLines = 5
            )

            //Interface visual da imagem
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .clickable { launcher.launch("image/*") }, // Clica em qualquer lugar da área
                contentAlignment = Alignment.Center
            ) {
                if (imageUri == null) {
                    // Estado Vazio: Mostra ícone e instrução
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto, // Precisa do import material-icons-extended
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Toque para adicionar uma foto", color = Color.Gray)
                    }
                } else {
                    // Estado Preenchido: Mostra a foto selecionada ocupando todo o espaço
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Imagem selecionada",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Pequeno selo de "Editar" por cima da foto
                    Surface (
                        modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp),
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp).size(20.dp)
                        )
                    }
                }
            }

            Button(
                onClick = {
                if (nome.isNotEmpty() && preco.isNotEmpty() && imagemCaminhoInterno != null){
                    val produtoFinal = Produto(
                        id = produtoEdicao?.id ?: 0,
                        nome = nome,
                        preco = "R$ $preco",
                        descricao = descricao,
                        imagemId = imagemCaminhoInterno!! //Converte Uri para String
                    )

                    onSalvarClick(produtoFinal)
                    backStack.removeAt(backStack.size -1)
                }
            }, modifier = Modifier.width(200.dp).height(50.dp)
            ) {
                Text(if (produtoEdicao == null) "Salvar Produto" else "Editar Produto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (backStack.isNotEmpty()) {
                        backStack.removeAt(backStack.size - 1)
                    }
                }, modifier = Modifier.width(200.dp).height(50.dp)
            ) {
                Text(text = "Cancelar")
            }

            Spacer(modifier = Modifier.height(32.dp))

        }
    }

}


fun copiaImagem(context: Context, uri: Uri): String?{
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)

        val nomeArquivo = "produto_${UUID.randomUUID()}.jpg"

        val arquivoDestino = File(context.filesDir, nomeArquivo)

        val outputStream = FileOutputStream(arquivoDestino)

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        arquivoDestino.absolutePath
    } catch (e: Exception){
        e.printStackTrace()
        null
    }
}

