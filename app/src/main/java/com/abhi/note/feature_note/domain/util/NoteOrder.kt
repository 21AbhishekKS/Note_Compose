package com.abhi.note.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType) {

    class Title(orderType: OrderType):NoteOrder(orderType)
    class Date(orderType: OrderType):NoteOrder(orderType)
    class Color(orderType: OrderType):NoteOrder(orderType)

    fun copy(orderType: OrderType) : NoteOrder {

        return when(this){

            is Title -> NoteOrder.Title(orderType)
            is Date -> NoteOrder.Date(orderType)
            is Color -> NoteOrder.Color(orderType)



        }

    }

}