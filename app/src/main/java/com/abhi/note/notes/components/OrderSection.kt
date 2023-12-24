package com.abhi.note.notes.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhi.note.feature_note.domain.util.NoteOrder
import com.abhi.note.feature_note.domain.util.OrderType

//@Preview(showBackground = true)
@Composable
fun OrderSection(
    modifier: Modifier =Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.descending),
    onOrderChange : (NoteOrder) -> Unit

){
    Column(modifier = modifier) {
        Row(modifier = modifier.fillMaxWidth()) {

            DefaultRadioButton(text ="Title" ,
                selected = noteOrder is NoteOrder.Title
            ) { onOrderChange(NoteOrder.Title(noteOrder.orderType)) }

            Spacer(modifier = Modifier.width(8.dp))


            DefaultRadioButton(
                text ="Date",
                selected = noteOrder is NoteOrder.Date
            ) { onOrderChange(NoteOrder.Date(noteOrder.orderType)) }

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text ="Color",
                selected = noteOrder is NoteOrder.Color
            ) { onOrderChange(NoteOrder.Color(noteOrder.orderType)) }

        }

        Spacer(modifier = Modifier.height(2.dp))

        Row {
            DefaultRadioButton(
                text ="Ascending",
                selected = noteOrder.orderType is OrderType.Ascending
            ) { onOrderChange(noteOrder.copy(OrderType.Ascending)) }

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text ="Descending",
                selected = noteOrder.orderType is OrderType.descending
            ) { onOrderChange(noteOrder.copy(OrderType.descending)) }
        }

    }
}