package com.example.testappandroid.RoomDB


import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore

@Entity(tableName = "expense")
data class Expense( val  time: String, val date: String,val battery: String,@PrimaryKey(autoGenerate = true) val id: Int ?= null) {

}
