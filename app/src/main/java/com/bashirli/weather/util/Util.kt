package com.bashirli.weather

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController


class CustomProgressBar(){
    companion object{
        fun progressDialog(context: Context): Dialog {
            val dialog=Dialog(context)
            val layout=LayoutInflater.from(context).inflate(R.layout.loading_layout,null)
            dialog.setContentView(layout)
            dialog.setCancelable(false)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            return dialog
        }
    }
}
fun generateCityToken(lat:Double,long:Double):String{
    val token=lat.toString()+","+long.toString()
    return token
}
