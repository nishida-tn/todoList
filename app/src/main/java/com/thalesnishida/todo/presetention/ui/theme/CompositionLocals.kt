package com.thalesnishida.todo.presetention.ui.theme

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.staticCompositionLocalOf

val LocalWindowSizeClass = staticCompositionLocalOf<WindowWidthSizeClass> {
    error("WindowSizeClass not provided. Did you forget to wrap your app in CompositionLocalProvider?")
}