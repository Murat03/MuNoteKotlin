package com.muratipek.munote.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muratipek.munote.model.Note

@Database(entities = arrayOf(Note::class), version = 1)
    abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
    //Singleton

    companion object{
        @Volatile private var instance: NoteDatabase ?= null

        private val lock = Any()

        operator fun invoke(context: Context) = instance?: synchronized(lock){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, NoteDatabase::class.java,
            "notedatabase").build()
    }
}