package com.example.notesapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.notesapp.models.Note

class InSqlNotesSource(context: Context) :
    SQLiteOpenHelper(context, "notes.db", null, 1), NotesSource {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE notes (
                id TEXT PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                text TEXT NOT NULL,
                createdAt TEXT NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS notes")
        onCreate(db)
    }

    override fun addNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id", note.id)
            put("title", note.name)
            put("text", note.text)
            put("createdAt", note.createdAt)
        }
        db.insert("notes", null, values)
    }

    override fun deleteNote(id: String) {
        val db = writableDatabase
        db.delete(
            "notes",
            "id = ?",
            arrayOf(id)
        )
        db.close()
    }

    override fun updateNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("title", note.name)
            put("text", note.text)
        }

        db.update(
            "notes",         // имя таблицы
            values,          // новые значения
            "id = ?",        // условие
            arrayOf(note.id)      // подставляем id
        )

        db.close()
    }

    override fun getAll(): List<Note> {
        val db = readableDatabase
        val cursor = db.query(
            "notes", arrayOf("id", "title", "text", "createdAt"),
            null, null, null, null, null
        )

        val result = mutableListOf<Note>()
        with(cursor) {
            while (moveToNext()) {
                result.add(
                    Note(
                        id = getString(getColumnIndexOrThrow("id")),
                        name = getString(getColumnIndexOrThrow("title")),
                        text = getString(getColumnIndexOrThrow("text")),
                        createdAt = getString(getColumnIndexOrThrow("createdAt"))
                    )
                )
            }
            close()
        }
        return result
    }
    
}