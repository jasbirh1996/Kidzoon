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
import com.htf.kidzoon.adapters.FeedsAdapter
import com.htf.kidzoon.models.Feeds
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.drawer_layout.view.*
import kotlinx.android.synthetic.main.fragment_feeds.view.*
import kotlinx.android.synthetic.main.home_toolbar.view.*
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class FeedsFragment : Fragment(),View.OnClickListener {
     private lateinit var currActivity: Activity
     private lateinit var rootView: View
    private var arrFeeds=ArrayList<Feeds>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_feeds, container, false)
        currActivity=activity!!
        rootView.tvTitle.text=getString(R.string.app_name)
        setListener()
        initRecycler()
        return rootView
    }

    private fun initRecycler() {
        arrFeeds.clear()
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        val mLayout=LinearLayoutManager(currActivity)
        rootView.recycler.layoutManager=mLayout
        val feedsAdapter=FeedsAdapter(currActivity,arrFeeds)
        rootView.recycler.adapter=feedsAdapter

    }

    private fun setListener() {
        rootView.ivSearch.setOnClickListener(this)
        rootView.ivMenu.setOnClickListener(this)
        rootView.btnNotification.setOnClickListener(this)
        rootView.flAddStory.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivSearch->{
                SearchActivity.open(currActivity)
            }
            R.id.ivMenu->{
               val activity= HomeActivity.homeActivity
                if (activity is HomeActivity){
                    (activity as HomeActivity).openDrawer()
                }
            }
            R.id.btnNotification->{
                NotificationActivity.open(currActivity)
            }
            R.id.flAddStory->{
                CreatePostActivity.open(currActivity)
            }
        }

    }


}
