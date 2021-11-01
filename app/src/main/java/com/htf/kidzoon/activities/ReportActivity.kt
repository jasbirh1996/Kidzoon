package com.htf.kidzoon.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.htf.kidzoon.R
import com.htf.kidzoon.adapters.ReportAdapter
import com.htf.kidzoon.models.Report
import kotlinx.android.synthetic.main.activity_report.*

class ReportActivity : LocalizeDarkActivity(), View.OnClickListener {
    private var currActivity: Activity =this
    private var arrReport=ArrayList<Report>()

    companion object{
        fun open(currActivity: Activity){
            val intent= Intent(currActivity,ReportActivity::class.java)
            currActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)
        setListener()
        initRecycler()
    }

    private fun initRecycler() {
        arrReport.clear()
        arrReport.add(Report())
        arrReport.add(Report())
        arrReport.add(Report())
        arrReport.add(Report())
        arrReport.add(Report())
        arrReport.add(Report())
        arrReport.add(Report())
        arrReport.add(Report())
        val mLayout=GridLayoutManager(currActivity,3)
        rvReportProblem.layoutManager=mLayout
        val reportAdapter=ReportAdapter(currActivity, arrReport)
        rvReportProblem.adapter=reportAdapter
    }

    private fun setListener() {
        ivClose.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.ivClose->{
                onBackPressed()
            }
        }

    }
}
