package com.thalesnishida.todo.core.provider

interface StringProvider {
    fun getString(resId: Int, vararg args: Any): String
}