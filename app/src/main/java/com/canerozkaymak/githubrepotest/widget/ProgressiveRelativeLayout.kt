package com.canerozkaymak.githubrepotest.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.canerozkaymak.githubrepotest.R

class ProgressiveRelativeLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle){

    lateinit var progressBar: ProgressBar

    init {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context)
            .inflate(R.layout.progressive_relative_layout, this, true)

        progressBar = findViewById(R.id.progressBar)
    }

    fun stopLoading(){
        progressBar.visibility = View.GONE
    }

    fun startLoading(){
        progressBar.visibility = View.VISIBLE
    }

}
