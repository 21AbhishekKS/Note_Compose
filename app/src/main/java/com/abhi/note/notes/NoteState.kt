package com.abhi.note.notes

import com.abhi.note.feature_note.domain.model.Note
import com.abhi.note.feature_note.domain.util.NoteOrder
import com.abhi.note.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder= NoteOrder.Date(orderType = OrderType.descending),
    val isOrderVisible : Boolean = false
)
