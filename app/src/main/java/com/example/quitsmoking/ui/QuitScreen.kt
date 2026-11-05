package com.example.quitsmoking.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.quitsmoking.domain.Achievement
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuitScreen(vm: QuitViewModel) {
    val state by vm.state.collectAsStateWithLifecycle()

    if (!state.started) {
        Column(
            Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Готовы бросить?", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(24.dp))
            Button(onClick = { vm.start() }) { Text("Я бросил курить") }
        }
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Без сигарет", style = MaterialTheme.typography.titleMedium)
                    Text(
                        formatDuration(state.elapsedMs),
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        state.stats?.let { s ->
            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(Modifier.weight(1f)) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Не выкурено", style = MaterialTheme.typography.bodySmall)
                            Text("${s.notSmoked}", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Card(Modifier.weight(1f)) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Сэкономлено", style = MaterialTheme.typography.bodySmall)
                            Text("${s.moneySaved.roundToInt()} ₽", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        state.nextMilestone?.let { m ->
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("Следующий этап: ${m.title}", fontWeight = FontWeight.Bold)
                        Text(m.description, style = MaterialTheme.typography.bodySmall)
                        Spacer(Modifier.height(8.dp))
                        Text("Осталось: ${formatDuration(state.msToNextMilestone)}")
                    }
                }
            }
        }

        state.nextAchievement?.let { a ->
            item {
                Card(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${a.emoji} Следующее достижение: ${a.title}",
                            fontWeight = FontWeight.Bold)
                        Text("Осталось: ${formatDuration(state.msToNextAchievement)}")
                    }
                }
            }
        }

        item { Text("Достижения", style = MaterialTheme.typography.titleMedium) }
        items(state.unlockedAchievements) { a ->
            Text("${a.emoji} ${a.title}", modifier = Modifier.padding(vertical = 4.dp))
        }

        item {
            OutlinedButton(
                onClick = { vm.reset() },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Сбросить") }
        }
        item { Spacer(Modifier.height(24.dp)) }
    }
}

fun formatDuration(ms: Long): String {
    val totalSec = ms / 1000
    val d = totalSec / 86400
    val h = (totalSec % 86400) / 3600
    val m = (totalSec % 3600) / 60
    val s = totalSec % 60
    return if (d > 0) "$d д $h ч $m м" else "$h ч $m м $s с"
}
