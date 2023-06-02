package com.bashirli.weather.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bashirli.weather.setImageCondition

object BindingAdapters{
    @JvmStatic
    @BindingAdapter("app:setCustomTimeText")
    fun setCustomTimeText(view: TextView,text:String){
        val time=text.substring(11)
        view.text=time
    }

    @JvmStatic
    @BindingAdapter("app:setCustomDate")
    fun setCustomDate(view:TextView,text:String){
        val date=text.substring(5)
        view.text=date
    }

    @JvmStatic
    @BindingAdapter("app:setConditionImage")
    fun setConditionImage(view: ImageView, url:String?){
       url?.let {
           view.setImageCondition(url,view.context)
       }
    }


}


