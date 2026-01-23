package com.example.interviewaicoach.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.interviewaicoach.R
import com.example.interviewaicoach.presentation.screens.NavGraphs
import com.example.interviewaicoach.presentation.theme.InterviewAiCoachTheme
import com.example.interviewaicoach.presentation.theme.myBackGround
import com.example.interviewaicoach.presentation.utils.getLanguage
import com.ramcosta.composedestinations.DestinationsNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_InterviewAiCoach)
        getLanguage()

        enableEdgeToEdge()

        setContent {
            InterviewAiCoachTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = myBackGround
                ) { paddingValues ->

                    Column(modifier = Modifier.padding(paddingValues)) {

                        DestinationsNavHost(
                            navController = navController,
                            navGraph = NavGraphs.root,
                        )
                    }
                }
            }
        }
    }
}
