package com.htf.kidzoon.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.PhotoAdapters
import com.htf.kidzoon.models.Photo
import kotlinx.android.synthetic.main.layout_recycler_view.*
import kotlinx.android.synthetic.main.layout_recycler_view.view.*

/**
 * A simple [Fragment] subclass.
 */
class PhotosFragment : Fragment(),View.OnClickListener {
    private lateinit var currActivity: Activity
    private lateinit var rootView: View
    private var arrPhotos=ArrayList<Photo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_photos, container, false)
        currActivity=activity!!
        initRecycler()
        return rootView
    }

    private fun initRecycler() {
        arrPhotos.clear()
        arrPhotos.add(Photo())
        arrPhotos.add(Photo())
        arrPhotos.add(Photo())
        arrPhotos.add(Photo())
        arrPhotos.add(Photo())
        val mLayout=GridLayoutManager(currActivity,2)
        rootView.recycler.layoutManager=mLayout
        val photoAdapter=PhotoAdapters(currActivity,arrPhotos)
        rootView.recycler.adapter=photoAdapter

    }

    override fun onClick(v: View?) {

    }

}
