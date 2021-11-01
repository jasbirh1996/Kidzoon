package com.htf.kidzoon.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.htf.kidzoon.R
import com.htf.kidzoon.models.Friends
import kotlinx.android.synthetic.main.fragment_find_friends.view.*

/**
 * A simple [Fragment] subclass.
 */
class FindFriendsFragment : Fragment(),View.OnClickListener {
    private lateinit var currActivity: Activity
    private lateinit var rootView: View
    private var friends=Friends()

    companion object{
        fun create(friends: Friends):FindFriendsFragment{
            var fragment=FindFriendsFragment()
            var bundle=Bundle()
            bundle.putSerializable("friends",friends)
            fragment.arguments=bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_find_friends, container, false)
        currActivity=activity!!
        friends=arguments!!.getSerializable("friends")as Friends
        rootView.ivFriendsImage.setImageResource(R.drawable.profile_image)
        return rootView

    }

    override fun onClick(v: View?) {

    }

}
