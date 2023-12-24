package com.abhi.note.feature_note.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi.note.ui.theme.BabyBlue
import com.abhi.note.ui.theme.LightGreen
import com.abhi.note.ui.theme.RedOrange
import com.abhi.note.ui.theme.RedPink
import com.abhi.note.ui.theme.Violet

@Entity
data class Note(
    val title : String,
    val content : String,
    val timeStamp : Long,
    val color : Int,
    @PrimaryKey val id : Int? = null
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteException(message : String) : Exception(message)