package com.example.quitsmoking.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.quitsmoking.data.Prefs
import com.example.quitsmoking.domain.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiState(
    val started: Boolean = false,
    val elapsedMs: Long = 0,
    val stats: Stats? = null,
    val nextMilestone: HealthMilestone? = null,
    val msToNextMilestone: Long = 0,
    val nextAchievement: Achievement? = null,
    val msToNextAchievement: Long = 0,
    val unlockedAchievements: List<Achievement> = emptyList()
)

class QuitViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = Prefs(app)
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        if (prefs.startTime > 0) startTicking() else _state.value = UiState(started = false)
    }

    fun start() {
        prefs.startTime = System.currentTimeMillis()
        startTicking()
    }

    fun reset() {
        prefs.startTime = 0L
        _state.value = UiState()
    }

    private fun startTicking() {
        viewModelScope.launch {
            while (true) {
                val elapsed = System.currentTimeMillis() - prefs.startTime
                updateState(elapsed)
                delay(1000)
            }
        }
    }

    private fun updateState(elapsedMs: Long) {
        val stats = computeStats(elapsedMs, prefs.cigsPerDay, prefs.packPrice, prefs.cigsPerPack)
        val minutes = elapsedMs / 60_000

        val nextMilestone = MILESTONES.firstOrNull { it.minutes > minutes }
        val msToNextMilestone = nextMilestone?.let { (it.minutes - minutes) * 60_000 } ?: 0

        val nextAchievement = ACHIEVEMENTS.firstOrNull { it.requiredMinutes > minutes }
        val msToNextAchievement = nextAchievement?.let { (it.requiredMinutes - minutes) * 60_000 } ?: 0

        val unlocked = ACHIEVEMENTS.filter { it.requiredMinutes <= minutes }

        _state.value = UiState(
            started = true,
            elapsedMs = elapsedMs,
            stats = stats,
            nextMilestone = nextMilestone,
            msToNextMilestone = msToNextMilestone,
            nextAchievement = nextAchievement,
            msToNextAchievement = msToNextAchievement,
            unlockedAchievements = unlocked
        )
    }
}
