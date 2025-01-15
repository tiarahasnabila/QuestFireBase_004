package com.example.prak10firebase.ui.home.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prak10firebase.model.Mahasiswa
import com.example.prak10firebase.navigation.DestinasiDetail
import com.example.prak10firebase.repository.RepositoryMhs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class DetailMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,
) : ViewModel() {
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.NIM])

    val detailUiState: StateFlow<DetailUiState> = repositoryMhs.getMhs(_nim)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue =  DetailUiState(
                isLoading = true,
            ),
        )

    fun deleteMhs(){
        detailUiState.value.detailUiEvent.toMhsModel().let {
            viewModelScope.launch {
                repositoryMhs.deleteMhs(it)
            }
        }
    }

}
//data class untuk menampung data yang akan ditampilkan di UI
data class DetailUiState(
    val detailUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isLoading : Boolean = false,
    val isError: Boolean = false,
    val errorMessage : String = ""
){
}

//memindahkan data dari entry ke ui
fun Mahasiswa.toDetailUiEvent() : MahasiswaEvent{
    return MahasiswaEvent(
        nim = nim,
        nama = nama,
        jenisKelamin = jenisKelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan,
        judulskripsi = judulskripsi,
        DosenPembimbing1 = DosenPembimbing1,
        DosenPembimbing2 = DosenPembimbing2
    )
}