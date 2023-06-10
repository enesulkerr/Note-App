package com.example.note_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper



class DB(context: Context) : SQLiteOpenHelper(context, DBName, null, Version  ) {

    companion object {
        private val DBName = "notes.db"
        private val Version = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val noteTable = "CREATE TABLE \"note\" (\n" +
                "\t\"nid\"\tINTEGER,\n" +
                "\t\"title\"\tTEXT,\n" +
                "\t\"detail\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"nid\" AUTOINCREMENT)\n" +
                ");"
        p0?.execSQL(noteTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val noteTableDrop = "DROP TABLE IF EXISTS note"
        p0?.execSQL(noteTableDrop)
        onCreate(p0)
    }


    fun addNote( title:String, detail: String) : Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("detail", detail)

        val effectRow = db.insert("note", null, values)
        db.close()
        return effectRow
    }

    fun deleteNote(nid: Int) : Int {
        val db = this.writableDatabase
        val status = db.delete("note", "nid = $nid", null )
        db.close()
        return status
    }

    fun  updateNote(title:String, detail: String, nid: Int) : Int {
        val db = this.writableDatabase
        val content = ContentValues()
        content.put("title", title)
        content.put("detail", detail)
        val status = db.update("note", content, "nid = $nid", null)
        db.close()
        return status
    }

    fun allNote() : List<Note> {
        val db = this.readableDatabase
        val arr = mutableListOf<Note>()
        val cursor = db.query("note",null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            val nid = cursor.getInt(0)
            val title = cursor.getString(1)
            val detail = cursor.getString(2)
            val note = Note(nid, title, detail)
            arr.add(note)
        }
        db.close()
        return arr
    }

}