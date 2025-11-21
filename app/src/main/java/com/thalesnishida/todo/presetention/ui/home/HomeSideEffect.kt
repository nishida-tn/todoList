package com.thalesnishida.todo.presetention.ui.home

sealed class HomeSideEffect {
    data class ShowToast(val message: String) : HomeSideEffect()
    object ScrollToTop : HomeSideEffect()
}