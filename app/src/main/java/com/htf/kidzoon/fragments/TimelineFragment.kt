package com.htf.kidzoon.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.FeedsAdapter
import com.htf.kidzoon.models.Feeds
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class TimelineFragment : Fragment(),View.OnClickListener {
    private lateinit var currActivity: Activity
    private lateinit var rootView:View
    private var arrFeeds=ArrayList<Feeds>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       rootView= inflater.inflate(R.layout.fragment_timeline, container, false)
        currActivity=activity!!
        initRecycler()
        return rootView
    }

    private fun initRecycler() {
        arrFeeds.clear()
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        arrFeeds.add(Feeds())
        val mLayout=LinearLayoutManager(currActivity)
        rootView.recycler.layoutManager=mLayout
        val feedsAdapter=FeedsAdapter(currActivity,arrFeeds)
        rootView.recycler.adapter=feedsAdapter

    }

    override fun onClick(v: View?) {

    }

}
