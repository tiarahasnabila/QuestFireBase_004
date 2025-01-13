package com.example.prak10firebase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.prak10firebase.ui.home.pages.HomeScreen
import com.example.prak10firebase.ui.home.pages.InsertMhsView

@Composable
fun PengelolaHalaman(
    modifier: Modifier,
    navController: NavHostController = rememberNavController()
){
