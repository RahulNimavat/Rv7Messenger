package com.example.rv7messenger.Adapter

import com.example.rv7messenger.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatFromAdapter: Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }
}