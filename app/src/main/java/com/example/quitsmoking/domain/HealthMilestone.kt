package com.example.quitsmoking.domain

data class HealthMilestone(
    val minutes: Long,
    val title: String,
    val description: String
)

val MILESTONES = listOf(
    HealthMilestone(20, "20 минут", "Пульс и давление приходят в норму"),
    HealthMilestone(12 * 60, "12 часов", "Уровень CO в крови падает до нормы"),
    HealthMilestone(24 * 60, "24 часа", "Снижается риск инфаркта"),
    HealthMilestone(48 * 60, "2 дня", "Восстанавливаются вкус и обоняние"),
    HealthMilestone(14 * 24 * 60, "2 недели", "Улучшается кровообращение"),
    HealthMilestone(30 * 24 * 60, "1 месяц", "Кашель и одышка уменьшаются")
)
