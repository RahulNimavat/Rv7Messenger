package com.example.rv7messenger.Adapter

import com.example.rv7messenger.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatToAdapter: Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
    }
}