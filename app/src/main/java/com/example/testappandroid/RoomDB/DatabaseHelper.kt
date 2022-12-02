package com.example.myapplication.RoomDB

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.example.testappandroid.RoomDB.Expense

@Database(entities = [Expense::class], exportSchema = false, version = 1)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun dao(): DAO?

    companion object {
        private const val DB_NAME = "expensedb"
        private var instance: DatabaseHelper? = null
        @Synchronized
        fun getDB(context: Context?): DatabaseHelper? {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, DatabaseHelper::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}