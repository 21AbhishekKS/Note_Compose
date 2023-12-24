package com.abhi.note.feature_note.data.repository

import androidx.room.Dao
import com.abhi.note.feature_note.data.data_source.NoteDao
import com.abhi.note.feature_note.domain.model.Note
import com.abhi.note.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepoImplement(private val dao: NoteDao) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun insert(note: Note) {
        dao.insert(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.delete(note)
    }
}