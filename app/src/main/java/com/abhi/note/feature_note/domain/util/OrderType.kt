package com.abhi.note.feature_note.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object descending : OrderType()
}