package com.example.terrenosventa.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.terrenosventa.model.RealEstate
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list_screen") {
        composable("list_screen") {
            RealEstateListScreen(navController = navController)
        }
        composable(
            route = "detail_screen/{item}",
            arguments = listOf(navArgument("item") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemJson = backStackEntry.arguments?.getString("item")
            val decodedItemJson = URLDecoder.decode(itemJson, StandardCharsets.UTF_8.toString())
            val item = Gson().fromJson(decodedItemJson, RealEstate::class.java)
            RealEstateDetailScreen(item = item, navController = navController)
        }
    }
}
