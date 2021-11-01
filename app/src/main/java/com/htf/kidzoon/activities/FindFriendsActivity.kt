package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapter.ViewPagerAdapter
import com.htf.kidzoon.fragments.FindFriendsFragment
import com.htf.kidzoon.models.Friends
import kotlinx.android.synthetic.main.activity_find_friends.*


class FindFriendsActivity : LocalizeDarkActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrFriends=ArrayList<Friends>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,FindFriendsActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_friends)
        setListener()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        arrFriends.clear()
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        arrFriends.add(Friends())
        val adapter=ViewPagerAdapter(supportFragmentManager)
        for (friends in arrFriends)
        adapter.addFragment(FindFriendsFragment.create(friends),"")
        viewpagerTop.adapter=adapter
        viewpagerTop.addOnPageChangeListener(object:ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

                val width = viewPagerBackground.width
                viewPagerBackground.scrollTo((width * position + width * positionOffset).toInt(), 0)
            }

            override fun onPageSelected(position: Int) {
            }

        })




    }

    private fun setListener() {
        ivBtnBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivBtnBack->{
                onBackPressed()
            }
        }

    }
}
