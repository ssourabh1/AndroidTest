package com.example.testappandroid.Mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testappandroid.RoomDB.Expense

class NoteViewModel(
    private val repository: NoteRepository
): ViewModel() {

    suspend fun insertNote(expense: Expense) = repository.insertNote(expense)

    fun getAllNotes() = repository.getAllNotes()
}
