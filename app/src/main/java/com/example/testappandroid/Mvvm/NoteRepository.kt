package com.example.testappandroid.Mvvm

import androidx.lifecycle.LiveData
import com.example.myapplication.RoomDB.DatabaseHelper
import com.example.testappandroid.RoomDB.Expense

class NoteRepository(
    private val noteDatabase: DatabaseHelper
) {

     fun insertNote(expense: Expense) = noteDatabase.dao()?.addTx(expense)

     fun getAllNotes(): List<Expense?>? = noteDatabase.dao()?.getalllist()
}