package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.ChatAdapter
import com.htf.kidzoon.models.Chat
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class ChatDetailActivity : LocalizeActivity(), View.OnClickListener {
    private var currActivity: Activity =this
    private var arrChat=ArrayList<Chat>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,ChatDetailActivity::class.java)
            currActivity.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)
        setListener()
        initializer()
        initRecycler()
    }

    private fun initializer() {
        ivOption.visibility=View.VISIBLE
        tvTitle.text="Saad Almubaraaz"
        tvStatus.visibility=View.VISIBLE
        tvStatus.text=getString(R.string.online)
    }

    private fun initRecycler() {
        arrChat.clear()
        arrChat.add(Chat())
        val mLayout=LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        val chatAdapter=ChatAdapter(currActivity,arrChat)
        recycler.adapter=chatAdapter

    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
        ivOption.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.ivOption->{
                openPopUpMenu()
            }
        }

    }

    private fun openPopUpMenu() {
        var popup: PopupMenu? = null
        popup = PopupMenu(this, ivOption)
        popup.inflate(R.menu.chat_popup_menu)

        popup.show()


    }


}
