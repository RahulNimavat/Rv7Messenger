package com.example.rv7messenger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.example.rv7messenger.Adapter.ChatFromAdapter
import com.example.rv7messenger.Adapter.ChatToAdapter
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*

class ChatLog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat Log"

        val adapter = GroupAdapter<ViewHolder>()

        adapter.add(ChatFromAdapter())
        adapter.add(ChatToAdapter())
        adapter.add(ChatFromAdapter())
        adapter.add(ChatToAdapter())

        chatlog_recycler.adapter = adapter
        Log.d("adapter","set")

        chatlog_recycler.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)
        Log.d("layout","set")



    }
}
