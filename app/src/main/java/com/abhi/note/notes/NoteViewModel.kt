package com.abhi.note.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi.note.feature_note.domain.model.Note
import com.abhi.note.feature_note.domain.use_case.NoteUseCases
import com.abhi.note.feature_note.domain.util.NoteOrder
import com.abhi.note.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) :ViewModel(){


    private val _state = mutableStateOf(NoteState())
    val state : State<NoteState> = _state

    private var recentlyDeletedNote : Note? = null

    private var getNotesJob : Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.descending))
    }

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.Order ->{

                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType == event.noteOrder.orderType){
                    return
                }
                getNotes(event.noteOrder)

            }is NotesEvent.RestoreNote ->{

                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote  = null

                }

            }is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNotesUseCase(event.note)
                    recentlyDeletedNote = event.note
                }

            }is NotesEvent.ToggleOrderSection ->{

                _state.value = state.value.copy(
                    isOrderVisible = !state.value.isOrderVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){

        getNotesJob?.cancel()

        getNotesJob = noteUseCases.getNotesUseCase(noteOrder)
            .onEach {notes ->

                _state.value= state.value.copy(
                    notes= notes,
                    noteOrder= noteOrder
                )

            }
            .launchIn(viewModelScope)
    }
}