package com.example.prak10firebase

import android.app.Application
import com.example.prak10firebase.di.AppContainer
import com.example.prak10firebase.di.MahasiswaContainer

class MahasiswaApplications: Application() {
    lateinit var container: MahasiswaContainer
    override fun onCreate() {
        super.onCreate()
        container= MahasiswaContainer()
    }
}