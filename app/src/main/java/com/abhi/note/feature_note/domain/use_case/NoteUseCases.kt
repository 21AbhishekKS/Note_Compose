package com.abhi.note.feature_note.domain.use_case

data class NoteUseCases(
    val getNotesUseCase: GetNotesUseCase,
    val deleteNotesUseCase: DeleteNoteUseCase,
    val addNote: AddNote,
    val getNote: GetNote
)
