package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.PostAdapter
import com.htf.kidzoon.models.Post
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.otp_layout.view.*
import kotlinx.android.synthetic.main.transparent_toolbar.*

class CreatePostActivity : LocalizeDarkActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrCreatePost=ArrayList<Post>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,CreatePostActivity::class.java)
            currActivity.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        initializer()
        setListener()
    }



    private fun initializer() {
        tvTitle.text=getString(R.string.create_post)
        tvShare.visibility=View.VISIBLE

    }

    private fun setListener() {
        btnBack.setOnClickListener(this)
        llVisibility.setOnClickListener(this)
        ivYoutubeLink.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btnBack->{
                onBackPressed()
            }
            R.id.llVisibility->{
                SelectPrivacyActivity.open(currActivity)
            }
            R.id.ivYoutubeLink->{
                openDialog()

            }
        }

    }

    private fun openDialog() {
        val builder = android.app.AlertDialog.Builder(currActivity)
        val dialogLangView = currActivity.layoutInflater.inflate(R.layout.dialog_enter_youtube_url, null)
        builder.setView(dialogLangView)
        builder.setCancelable(true)
        var dialog = builder.show()


        dialogLangView.ivClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }
}
