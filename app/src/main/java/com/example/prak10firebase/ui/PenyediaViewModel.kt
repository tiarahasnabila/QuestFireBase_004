package com.example.prak10firebase.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.prak10firebase.MahasiswaApplications
import com.example.prak10firebase.ui.home.viewmodel.DetailMhsViewModel
import com.example.prak10firebase.ui.home.viewmodel.HomeViewModel
import com.example.prak10firebase.ui.home.viewmodel.InsertViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeViewModel(mahasiswaApp().container.repositoryMhs) }
        initializer { InsertViewModel(mahasiswaApp().container.repositoryMhs) }
        initializer { DetailMhsViewModel(createSavedStateHandle(), mahasiswaApp().container.repositoryMhs) }
    }
}

fun CreationExtras.mahasiswaApp(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)