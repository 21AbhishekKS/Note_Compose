package com.abhi.note.feature_note.presentation

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{

    data class EnteredTitel(val vlaue : String) : AddEditNoteEvent()
    data class ChangeTitleFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class EnteredContent(val vlaue : String) : AddEditNoteEvent()
    data class ChangeContentFocus(val focusState: FocusState) : AddEditNoteEvent()
    data class changeColor(val color : Int) : AddEditNoteEvent()
    object saveNote : AddEditNoteEvent()



}

