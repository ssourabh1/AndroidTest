package com.example.myapplication.RoomDB

import androidx.room.*
import com.example.testappandroid.RoomDB.Expense

@Dao
interface DAO {
    @Query("select * from expense")
    fun getalllist(): List<Expense?>?

    @Insert
    fun addTx(expense: Expense?)

    //@Query("UPDATE expense SET title=:title,amount=:amount WHERE title=:title1")
    //fun updateTx(title:String,amount:String,title1: String)

    //@Query("DELETE FROM expense WHERE title=:title1")
    //fun deleteTx(title1: String)

    @Query("delete from expense")
    fun deleteAllNotes()
}