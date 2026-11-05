package com.example.quitsmoking.data

import android.content.Context

class Prefs(context: Context) {
    private val sp = context.getSharedPreferences("quit_prefs", Context.MODE_PRIVATE)

    var startTime: Long
        get() = sp.getLong("start_time", 0L)
        set(v) = sp.edit().putLong("start_time", v).apply()

    var cigsPerDay: Int
        get() = sp.getInt("cigs_per_day", 20)
        set(v) = sp.edit().putInt("cigs_per_day", v).apply()

    var packPrice: Float
        get() = sp.getFloat("pack_price", 200f)
        set(v) = sp.edit().putFloat("pack_price", v).apply()

    var cigsPerPack: Int
        get() = sp.getInt("cigs_per_pack", 20)
        set(v) = sp.edit().putInt("cigs_per_pack", v).apply()
}
