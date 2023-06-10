package com.example.note_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*

    lateinit var titleText: EditText
    lateinit var detailText: EditText
    lateinit var btngotodetail: Button
    lateinit var titlelistview : ListView
    lateinit var db: DB

    lateinit var adapter: ArrayAdapter<String>




class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DB(this)
        titleText = findViewById(R.id.titleText)
        detailText = findViewById(R.id.detailText)
        btngotodetail = findViewById(R.id.btngotodetail)
        titlelistview = findViewById(R.id.titlelistview)



        val ls = db.allNote()
        var titleList = mutableListOf<String>()
        for (note in ls) {
            val title = note.title
            titleList.add(title)
        }
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titleList)
        titlelistview.adapter = adapter


        btngotodetail.setOnClickListener {
            var title = titleText.text
            var detail = detailText.text
            db.addNote(title.toString(), detail.toString())

            val ls = db.allNote()
            var titleList = mutableListOf<String>()
            for (note in ls) {
                val title = note.title
                titleList.add(title)
            }
            adapter.clear()
            adapter.addAll(titleList)
            adapter.notifyDataSetChanged()
        }


        titlelistview.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val ls = db.allNote()
            var detailList = mutableListOf<String>()
            var nidList = mutableListOf<Int>()
            for (note in ls) {
                val detail = note.detail
                val nid = note.nid
                nidList.add(nid)
                detailList.add(detail)
            }
            val clickedItemDetail = detailList[position]
            val clickedItem = titleList[position]
            val clikedItemNid = nidList[position]

            var intent=  Intent(this, detailwindow::class.java)
            intent.putExtra("clikedItem", clickedItem)
            intent.putExtra("clickedItemDetail",clickedItemDetail)
            intent.putExtra("position",clikedItemNid)
            startActivity(intent)
        }


    }


    override fun onResume() {
        super.onResume()

        // Adapter güncellenir
        val ls = db.allNote()
        val titleList = mutableListOf<String>()
        for (note in ls) {
            val title = note.title
            titleList.add(title)
        }
        adapter.clear()
        adapter.addAll(titleList)
        adapter.notifyDataSetChanged()
    }





}
