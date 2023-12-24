package com.abhi.note.feature_note.presentation.add_edit_note.components

import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.note.feature_note.domain.model.InvalidNoteException
import com.abhi.note.feature_note.domain.use_case.NoteUseCases
import com.abhi.note.feature_note.domain.util.NoteOrder
import com.abhi.note.feature_note.presentation.AddEditNoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(NoteTextFieldState(
        hint = "Enter Title..."
    ))
    val noteTitle : State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter some content"
    ))
    val noteContent : State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(com.abhi.note.feature_note.domain.model.Note.noteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEevent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId : Int?= null

    init {
        savedStateHandle.get<Int>("noteId")?.let {noteId ->
            if (noteId != -1){
                viewModelScope.launch {
                    noteUseCases.getNote(noteId) ?.also {note ->
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value= note.color
                    }
                }
            }
        }}

    fun onEvent(event : AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.EnteredTitel -> {
                _noteTitle.value= noteTitle.value.copy(
                    text = event.vlaue
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus ->{
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
         is AddEditNoteEvent.EnteredContent -> {
                _noteContent.value= noteContent.value.copy(
                    text = event.vlaue
                )
            }

            is AddEditNoteEvent.ChangeContentFocus ->{
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.changeColor ->{
                _noteColor.value = event.color
            }

            is AddEditNoteEvent.saveNote -> {
                viewModelScope.launch {
                    try{
                        noteUseCases.addNote(
                            com.abhi.note.feature_note.domain.model.Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEevent.saveNote)
                    }
                    catch (e : InvalidNoteException){
                        _eventFlow.emit(
                            UiEevent.ShowSnackBar(
                                message = e.message?: "Sorry couldn't save Note"
                            )
                        )
                    }
                }
            }
        }
    }
}

sealed class UiEevent{
    data class ShowSnackBar(val message : String) : UiEevent()
    object saveNote :UiEevent()
}