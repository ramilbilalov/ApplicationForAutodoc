package com.bilalov.testapplicationforappricot.connectionChecker

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

object ConnectionCheckerImpl: IConnectionChecker {
    @SuppressLint("ServiceCast")
    override fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}