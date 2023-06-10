package com.example.note_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class detailwindow : AppCompatActivity() {

    lateinit var detailgoster : TextView
    lateinit var btndelete : Button
    lateinit var itemgoster : TextView

    lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailwindow)

        db = DB(this)
        itemgoster = findViewById(R.id.titlegoster)
        btndelete = findViewById(R.id.btndelete)
        detailgoster = findViewById(R.id.detailgoster)

        val newdetail = intent.getStringExtra("clickedItemDetail")
        val newditem = intent.getStringExtra("clikedItem")
        val position = intent.getIntExtra("position",0)
        detailgoster.setText(newdetail)
        itemgoster.setText(newditem)


        btndelete.setOnClickListener {
            db.deleteNote(position)
            val intent = Intent(this, MainActivity::class.java)
            val ls = db.allNote()
            setResult(Activity.RESULT_OK, intent)
            finish()

        }



    }
}