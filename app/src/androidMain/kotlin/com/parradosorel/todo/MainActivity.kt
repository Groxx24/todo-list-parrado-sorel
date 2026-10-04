package com.parradosorel.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.parradosorel.todo.ui.App

class MainActivity : ComponentActivity() {
    private val container get() = (application as OurListsApplication).container

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent { App(container) }
    }
}
