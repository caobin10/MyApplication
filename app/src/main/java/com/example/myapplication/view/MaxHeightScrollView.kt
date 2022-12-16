package com.example.myapplication.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import com.example.myapplication.R

/**
 * Created by WLM on 2021/8/31
 * Desc:
 */
class MaxHeightScrollView: NestedScrollView {

    private var mMaxHeight = 0

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val arr =context.obtainStyledAttributes(attrs, R.styleable.MaxHeightRecyclerView)
        mMaxHeight = arr.getLayoutDimension(R.styleable.MaxHeightRecyclerView_maxHeight, mMaxHeight)
        arr.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        if (mMaxHeight > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    public fun setMaxHeight(mMaxHeight:Int){
        this.mMaxHeight = mMaxHeight
    }

}