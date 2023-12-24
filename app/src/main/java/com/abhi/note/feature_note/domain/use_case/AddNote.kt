package com.abhi.note.feature_note.domain.use_case

import com.abhi.note.feature_note.domain.model.InvalidNoteException
import com.abhi.note.feature_note.domain.model.Note
import com.abhi.note.feature_note.domain.repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {

    @Throws(InvalidNoteException ::class)
    suspend operator fun invoke(note : Note){
        if (note.title.isBlank()){
            throw InvalidNoteException("Title cant be empty")
        }

        if (note.content.isBlank()){
            throw InvalidNoteException("content cant be empty")
        }

        repository.insert(note)
    }
}