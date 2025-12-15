package com.thalesnishida.todo.domain.model

enum class TodoCategoryIcon(val value: String = "") {
    WORK("Work"),
    GROCERY("Grocery"),
    SPORT("Sport"),
    DESIGN("Design"),
    UNIVERSITY("University"),
    SOCIAL("Social"),
    MUSIC("Music"),
    HEALTH("Health"),
    HOME("Home"),
    MOVIE("Movie"),
    CREATE_NEW("Create New"),
    DEFAULT;

    companion object {
        fun fromName(name: String?): TodoCategoryIcon {
            return try {
                name?.let { valueOf(it) } ?: DEFAULT
            } catch (e: IllegalArgumentException) {
                DEFAULT
            }
        }
    }
}