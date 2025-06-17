package com.example.connect.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

object SharedPref {

    fun storeData(
        email: String,
        name: String,
        userName: String,
        bio: String,
        imageUrl: String,
        context: Context
    ){
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("users",Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("name",name)
        editor.putString("email",email)
        editor.putString("username",userName)
        editor.putString("bio",bio)
        editor.putString("imageUrl",imageUrl)

        editor.apply()
    }

    fun getUserName(context: Context): String{
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("username","")!!
    }
    fun getName(context: Context): String{
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("name","")!!
    }
    fun getEmail(context: Context): String{
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("email","")!!
    }
    fun getBio(context: Context): String{
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("bio","")!!
    }
    fun getImageUrl(context: Context): String{
        val sharedPreferences : SharedPreferences = context.getSharedPreferences("users",MODE_PRIVATE)
        return sharedPreferences.getString("imageUrl","")!!
    }
}