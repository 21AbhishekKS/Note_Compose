package com.abhi.note.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.abhi.note.feature_note.data.data_source.NoteDataBase
import com.abhi.note.feature_note.data.repository.NoteRepoImplement
import com.abhi.note.feature_note.domain.repository.NoteRepository
import com.abhi.note.feature_note.domain.use_case.AddNote
import com.abhi.note.feature_note.domain.use_case.DeleteNoteUseCase
import com.abhi.note.feature_note.domain.use_case.GetNote
import com.abhi.note.feature_note.domain.use_case.GetNotesUseCase
import com.abhi.note.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(app : Application) : NoteDataBase{

        return Room.databaseBuilder(
            app,
            NoteDataBase::class.java,
            NoteDataBase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db :NoteDataBase) : NoteRepository {

        return NoteRepoImplement(db.noteDao)

    }

    @Provides
    @Singleton
    fun provideUseCases(repository: NoteRepository) : NoteUseCases {

        return NoteUseCases(
            getNotesUseCase = GetNotesUseCase(repository),
            deleteNotesUseCase = DeleteNoteUseCase(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )

    }

}