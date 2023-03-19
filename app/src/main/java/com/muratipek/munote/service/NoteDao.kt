package com.muratipek.munote.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muratipek.munote.model.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE uuid = :noteId")
    suspend fun getNote(noteId: Int): Note

    @Insert
    suspend fun insertAll(vararg note: Note): List<Long>

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}