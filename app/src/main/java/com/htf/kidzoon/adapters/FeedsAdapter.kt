package com.htf.kidzoon.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.RecyclerView
import com.htf.kidzoon.R
import com.htf.kidzoon.activities.FeedDetailActivity
import com.htf.kidzoon.models.Feeds
import kotlinx.android.synthetic.main.row_feeds.view.*

class FeedsAdapter (
    private var currActivity: Activity,
    private var arrFeeds:ArrayList<Feeds>
):RecyclerView.Adapter<FeedsAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        init {
            itemView.btn_Options.setOnClickListener {
             showMenu(itemView)
            }
            itemView.setOnClickListener {
                FeedDetailActivity.open(currActivity)
            }


        }

    }

    @SuppressLint("RestrictedApi")
    private fun showMenu(itemView: View) {
        val menuBuilder= MenuBuilder(currActivity)
        val inflater= MenuInflater(currActivity)
        inflater.inflate(R.menu.feed_post_popup_menu,menuBuilder)
        val optionsMenu=MenuPopupHelper(currActivity,menuBuilder,itemView,false,R.attr.actionOverflowMenuStyle,R.style.AppTheme)
        optionsMenu.gravity=Gravity.BOTTOM or Gravity.END

        optionsMenu.show()


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedsAdapter.MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.row_feeds,parent,false)
        return MyViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return arrFeeds.size
    }

    override fun onBindViewHolder(holder: FeedsAdapter.MyViewHolder, position: Int) {
        val model=arrFeeds[position]
    }

}