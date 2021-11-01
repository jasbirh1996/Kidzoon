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
import com.htf.kidzoon.adapters.PostAdapter
import com.htf.kidzoon.models.Post
import kotlinx.android.synthetic.main.activity_edit_post.*
import kotlinx.android.synthetic.main.otp_layout.view.*
import kotlinx.android.synthetic.main.transparent_toolbar.*

class EditPostActivity : LocalizeDarkActivity(),View.OnClickListener {
    private var currActivity: Activity =this
    private var arrPost=ArrayList<Post>()

    companion object{
        fun open(currActivity: Activity){
            val intent=Intent(currActivity,EditPostActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)
        setListener()
        initRecycler()
        initializer()
    }

    private fun initializer() {
        tvShare.visibility=View.VISIBLE
        tvTitle.text=getString(R.string.edit_post)
    }

    private fun initRecycler() {
        arrPost.clear()
        arrPost.add(Post())
        arrPost.add(Post())
        arrPost.add(Post())
        arrPost.add(Post())
        arrPost.add(Post())
        arrPost.add(Post())
        val mLayout=LinearLayoutManager(currActivity,LinearLayoutManager.HORIZONTAL,false)
        rvPostMedia.layoutManager=mLayout
        val postAdapter=PostAdapter(currActivity,arrPost)
        rvPostMedia.adapter=postAdapter


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


        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

    }
}
