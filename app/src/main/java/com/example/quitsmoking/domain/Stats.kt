package com.example.quitsmoking.domain

data class Stats(
    val elapsedMinutes: Long,
    val notSmoked: Int,
    val moneySaved: Float,
    val lifeRegainedMinutes: Long
)

fun computeStats(
    elapsedMs: Long,
    cigsPerDay: Int,
    packPrice: Float,
    cigsPerPack: Int
): Stats {
    val minutes = elapsedMs / 60_000
    val notSmoked = (minutes / (24 * 60.0) * cigsPerDay).toInt()
    val pricePerCig = packPrice / cigsPerPack
    val moneySaved = notSmoked * pricePerCig
    val lifeRegained = notSmoked * 11L
    return Stats(minutes, notSmoked, moneySaved, lifeRegained)
}

data class Achievement(
    val id: String,
    val title: String,
    val emoji: String,
    val requiredMinutes: Long
)

val ACHIEVEMENTS = listOf(
    Achievement("first_hour", "Первый час", "⏰", 60),
    Achievement("one_day", "Сутки", "🌙", 24 * 60),
    Achievement("three_days", "Три дня", "🔥", 3 * 24 * 60),
    Achievement("one_week", "Неделя", "📅", 7 * 24 * 60),
    Achievement("one_month", "Месяц", "🏅", 30 * 24 * 60),
    Achievement("one_year", "Год", "🏆", 365L * 24 * 60)
)
