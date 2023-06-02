package com.bashirli.weather
import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.json.JSONArray
import java.util.ArrayList

fun ImageView.setImageURL(url:String,context: Context){
    val options=RequestOptions.placeholderOf(placeHolder(context))
        .error(R.drawable.ic_launcher_foreground)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun ImageView.setImageCondition(url:String,context: Context){
    this.setImageURL(CUSTOM_HTTPS+url,context)
}

private fun placeHolder(context:Context):CircularProgressDrawable{
    val circularProgressDrawable=CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth=8f
    circularProgressDrawable.centerRadius=40f
    circularProgressDrawable.start()
    return circularProgressDrawable
}

fun JSONArray.toArrayList():ArrayList<String>{
    val list= arrayListOf<String>()
    for(i in 0 until this.length()){
        list.add(this.getString(i))
    }
    return list
}