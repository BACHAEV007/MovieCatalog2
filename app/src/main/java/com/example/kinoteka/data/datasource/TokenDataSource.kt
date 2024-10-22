package com.example.kinoteka.data.datasource

import android.content.Context
import android.content.SharedPreferences
import com.example.kinoteka.constants.Constants.SHARED_PREFERENCES_NAME
import com.example.kinoteka.constants.Constants.TOKEN


class TokenDataSource(context: Context) {
    var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    fun saveToken(token: String) {
        sharedPreferences.edit()
            .putString(TOKEN, token)
            .apply()
    }
    fun getToken() : String? {
        return sharedPreferences.getString(TOKEN,null)
    }
}