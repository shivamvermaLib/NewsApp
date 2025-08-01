package com.app.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.newsapp.ui.screens.NewsDetailsScreen
import com.app.newsapp.ui.viewmodel.NewsDetailsViewModel
import com.app.newsapp.ui.screens.NewsListScreen
import com.app.newsapp.ui.viewmodel.NewsListViewModel
import com.app.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                NavigationStack()
            }
        }
    }
}

@Composable
fun NavigationStack() {
    val navController = rememberNavController()
    val newsListViewModel = hiltViewModel<NewsListViewModel>()
    val newsDetailsViewModel = hiltViewModel<NewsDetailsViewModel>()

    NavHost(navController = navController, startDestination = Screen.NewsList.route) {
        composable(route = Screen.NewsList.route) {
            val uiState by newsListViewModel.newsListUIStateFlow.collectAsStateWithLifecycle()
            NewsListScreen(uiState,{
                newsDetailsViewModel.setArticle(it)
                navController.navigate(Screen.Detail.route)
            },{
                newsListViewModel.refresh()
            })
        }
        composable(route = Screen.Detail.route) {
            val uiState by newsDetailsViewModel.uiStateFlow.collectAsStateWithLifecycle()
            NewsDetailsScreen(uiState) { navController.popBackStack() }
        }
    }
}

sealed class Screen(val route: String) {
    object NewsList : Screen("list_screen")
    object Detail : Screen("detail_screen")
}
