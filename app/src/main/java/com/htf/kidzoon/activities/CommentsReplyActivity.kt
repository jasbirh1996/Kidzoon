package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.CommentsAdapter
import com.htf.kidzoon.models.Comments
import kotlinx.android.synthetic.main.activity_comments_reply.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class CommentsReplyActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity:Activity=this
    private var arrComments=ArrayList<Comments>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,CommentsReplyActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments_reply)
        initRecycler()
        setListener()
    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
    }

    private fun initRecycler() {
        arrComments.clear()
        arrComments.add(Comments())
        arrComments.add(Comments())
        arrComments.add(Comments())
        arrComments.add(Comments())
        val mLayout=LinearLayoutManager(currActivity)
        rvReplyComment.layoutManager=mLayout
        val replyAdapter=CommentsAdapter(currActivity, arrComments)
        rvReplyComment.adapter=replyAdapter

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
        }

    }
}
