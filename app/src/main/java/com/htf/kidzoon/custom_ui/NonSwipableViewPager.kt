package app.wm.chinatravel.custom_ui

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NonSwipableViewPager : ViewPager {

    private var isEnable = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return this.isEnable && super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return this.isEnable && super.onTouchEvent(ev)

    }

    fun setPagingEnabled(enabled: Boolean) {
        this.isEnable = enabled
    }
}
