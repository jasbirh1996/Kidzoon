package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.CommentsAdapter
import com.htf.kidzoon.models.Comments
import kotlinx.android.synthetic.main.activity_feed_detail.*
import kotlinx.android.synthetic.main.dialog_manage_post.view.*
import kotlinx.android.synthetic.main.dialog_manage_post.view.rlMain
import kotlinx.android.synthetic.main.dialog_who_can_see_post.view.*
import kotlinx.android.synthetic.main.red_background_toolbar.*

class FeedDetailActivity : LocalizeActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrComments=ArrayList<Comments>()

    companion object{
        fun open(currActivity:Activity){
            val intent= Intent(currActivity,FeedDetailActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_detail)
        initializer()
        setListener()
        initRecycler()
    }

    private fun initRecycler() {
        arrComments.clear()
        arrComments.add(Comments())
        arrComments.add(Comments())
        arrComments.add(Comments())
        arrComments.add(Comments())
        arrComments.add(Comments())
        val mLayout=LinearLayoutManager(currActivity)
        recycler.layoutManager=mLayout
        val commentsAdapter=CommentsAdapter(currActivity, arrComments)
        recycler.adapter=commentsAdapter

    }

    private fun initializer() {
       ivOption.visibility=View.VISIBLE
    }

    private fun setListener() {
        ivOption.setOnClickListener(this)
        btnBack.setOnClickListener(this)
        viewMore.setOnClickListener(this)
        btn_Options.setOnClickListener(this)
        ivVisibility.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.viewMore->{
                FeedCommentsActivity.open(currActivity)
            }
            R.id.btn_Options->{
                managePostDialog()
            }
            R.id.ivVisibility->{
                visibilityDialog()
            }
        }

    }

    private fun visibilityDialog() {
        val builder = android.app.AlertDialog.Builder(currActivity)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_who_can_see_post, null)
        builder.setView(dialogLangView)
        builder.setCancelable(true)
        val dialog = builder.show()

        dialogLangView.rlMain.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }

    private fun managePostDialog() {
        val builder = android.app.AlertDialog.Builder(currActivity)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_manage_post, null)
        builder.setView(dialogLangView)
        builder.setCancelable(true)
        val dialog = builder.show()
        dialogLangView.rlMain.setOnClickListener {
            dialog.dismiss()
        }
        dialogLangView.llReport.setOnClickListener {
            ReportActivity.open(currActivity)
            dialog.dismiss()
        }
        dialogLangView.llEditPost.setOnClickListener {
            EditPostActivity.open(currActivity)
            dialog.dismiss()
        }

        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setGravity(Gravity.BOTTOM)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }
}
