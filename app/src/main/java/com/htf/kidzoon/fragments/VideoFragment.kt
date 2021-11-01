package com.htf.kidzoon.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager

import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.VideoAdapters
import com.htf.kidzoon.models.Video
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class VideoFragment : Fragment(),View.OnClickListener {
    private lateinit var currActivity: Activity
    private lateinit var rootView: View
    private var arrVideo=ArrayList<Video>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_video, container, false)
        currActivity=activity!!
        initRecycler()
        return rootView
    }

    private fun initRecycler() {
        arrVideo.clear()
        arrVideo.add(Video())
        arrVideo.add(Video())
        arrVideo.add(Video())
        arrVideo.add(Video())
        arrVideo.add(Video())
        arrVideo.add(Video())
        val mLayout=GridLayoutManager(currActivity,2)
        rootView.recycler.layoutManager=mLayout
        val videoAdapter=VideoAdapters(currActivity, arrVideo)
        rootView.recycler.adapter=videoAdapter

    }

    override fun onClick(v: View?) {
        when(v!!.id){

        }

    }

}
