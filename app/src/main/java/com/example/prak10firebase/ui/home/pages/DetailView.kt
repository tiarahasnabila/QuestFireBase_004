package com.example.prak10firebase.ui.home.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prak10firebase.model.Mahasiswa
import com.example.prak10firebase.ui.PenyediaViewModel
import com.example.prak10firebase.ui.customewidget.TopAppBar
import com.example.prak10firebase.ui.home.viewmodel.DetailMhsViewModel
import com.example.prak10firebase.ui.home.viewmodel.DetailUiState
import com.example.prak10firebase.ui.home.viewmodel.toMhsModel

@Composable
fun DetailMhsView(
    modifier: Modifier = Modifier,
    viewModel: DetailMhsViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () ->Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
){
    Scaffold (
        topBar = {
            TopAppBar(
                judul = "Detail Mahasiswa",
                showBackButton = true,
                onBack = onBack,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {onEditClick(viewModel.detailUiState.value.detailUiEvent.nim)},
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mahasiswa"
                )
            }
        }
    ){ innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailMhs(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteMhs()
                onDeleteClick()
            }
        )
    }

}

@Composable
fun BodyDetailMhs(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () ->Unit = { }
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false)  }
    when{
        detailUiState.isLoading -> {
            Box(
                modifier=modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator() //Tampilkan loading
            }
        }

        detailUiState.isUiEventNotEmpty ->{
            Column(
                modifier=modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailMhs(
                    mahasiswa = detailUiState.detailUiEvent.toMhsModel(),
                    modifier = modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text(text = "Delete")
                }
                if (deleteConfirmationRequired){
                    DeleteConfirmationDialog(
                        onDeleteConfirm =
                        {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {deleteConfirmationRequired = false},
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailUiState.isUiEventEmpty -> {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Data tidak ditemukan",
                    modifier =Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun ItemDetailMhs(
    modifier: Modifier = Modifier,
    mahasiswa: Mahasiswa
){
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailMhs(judul = "NIM", isinya = mahasiswa.nim)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Nama", isinya = mahasiswa.nama)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Alamat", isinya = mahasiswa.alamat)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Jenis Kelamin", isinya = mahasiswa.jenisKelamin)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Kelas ", isinya = mahasiswa.kelas)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Angkatan", isinya = mahasiswa.angkatan)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Judul Skripsi", isinya = mahasiswa.judulskripsi)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Dosen Pembimbing 1", isinya = mahasiswa.DosenPembimbing1)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailMhs(judul = "Dosen Pembimbing 2", isinya = mahasiswa.DosenPembimbing2)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }

}

@Composable
fun ComponentDetailMhs(
    modifier: Modifier = Modifier,
    judul : String,
    isinya: String,
){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = "$isinya ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        onDismissRequest= { },
        title = {
            Text("Delete Data")
        },
        text = {
            Text("Apakah anda yakin ingin menghapus data?")
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel)
            {
                Text(text = "Cancel")
            }
        },
        confirmButton =
        {
            TextButton(onClick = onDeleteConfirm)
            {
                Text(text = "Yes")
            }
        }
    )
}