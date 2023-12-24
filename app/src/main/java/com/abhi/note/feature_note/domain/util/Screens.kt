package com.abhi.note.feature_note.domain.util

sealed class Screens(val route : String) {
    object NoteScreen : Screens("notes_screen")
    object AddEditScreen : Screens("add_edit_screen")

}