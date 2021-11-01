package com.htf.kidzoon.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.htf.kidzoon.R
import com.htf.kidzoon.activities.*
import com.htf.kidzoon.adapters.ChatListAdapter
import com.htf.kidzoon.models.ChatList
import kotlinx.android.synthetic.main.drawer_layout.view.*
import kotlinx.android.synthetic.main.home_toolbar.*
import kotlinx.android.synthetic.main.home_toolbar.view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class ChatFragment : Fragment(),View.OnClickListener {
    private lateinit var currActivity: Activity
    private lateinit var rootView: View
    private var arrChat=ArrayList<ChatList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       rootView=inflater.inflate(R.layout.fragment_chat, container, false)
        currActivity=activity!!
        initializer()
        initRecycler()
        setListener()
        return rootView
    }

    private fun setListener() {
        rootView.ivMenu.setOnClickListener(this)
    }

    private fun initializer() {
        rootView.tvTitle.text=getString(R.string.chat)
        rootView.llEnd.visibility=View.GONE


    }

    private fun initRecycler() {
        arrChat.clear()
        arrChat.add(ChatList())
        arrChat.add(ChatList())
        arrChat.add(ChatList())
        arrChat.add(ChatList())
        arrChat.add(ChatList())
        arrChat.add(ChatList())
        val mlayout=LinearLayoutManager(currActivity)
        rootView.recycler.layoutManager=mlayout
        val chatAdapter=ChatListAdapter(currActivity, arrChat)
        rootView.recycler.adapter=chatAdapter

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivMenu->{
                val activity= HomeActivity.homeActivity
                if (activity is HomeActivity){
                    (activity as HomeActivity).openDrawer()
                }
            }
        }

    }
}
