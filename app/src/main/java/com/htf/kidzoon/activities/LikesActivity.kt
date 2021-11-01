package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.LikeAdapter
import com.htf.kidzoon.models.Likes
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class LikesActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity:Activity=this
    private var arrLike=ArrayList<Likes>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,LikesActivity::class.java)
            currActivity.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_likes)
        initRecycler()
        tvTitle.text=getString(R.string.likes)
        setListener()
    }

    private fun initRecycler() {
        arrLike.clear()
        arrLike.add(Likes())
        arrLike.add(Likes())
        arrLike.add(Likes())
        arrLike.add(Likes())
        arrLike.add(Likes())
        arrLike.add(Likes())
        val mLayout=LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        val likeAdapter=LikeAdapter(currActivity, arrLike)
        recycler.adapter=likeAdapter


    }

    private fun setListener() {
        btnBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
        }

    }
}
