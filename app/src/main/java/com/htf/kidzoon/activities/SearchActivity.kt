package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.SearchAdapter
import com.htf.kidzoon.models.Search
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class SearchActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrSearch=ArrayList<Search>()


    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,SearchActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initRecycler()
        setListener()

    }

    private fun initRecycler() {
        arrSearch.clear()
        arrSearch.add(Search())
        arrSearch.add(Search())
        arrSearch.add(Search())
        arrSearch.add(Search())
        arrSearch.add(Search())
        arrSearch.add(Search())
        val mLayout=LinearLayoutManager(currActivity)
        rvSearch.layoutManager=mLayout
        val searchAdapter=SearchAdapter(currActivity, arrSearch)
        rvSearch.adapter=searchAdapter


    }

    private fun setListener() {
        cvFriendsNearMe.setOnClickListener(this)
        btnBack.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when(v!!.id){
          R.id.cvFriendsNearMe->{
              FindFriendsActivity.open(currActivity)
          }
            R.id.btnBack->{
                onBackPressed()
            }
        }

    }


}
