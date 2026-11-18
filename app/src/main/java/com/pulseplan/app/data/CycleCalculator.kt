package com.pulseplan.app.data

import java.time.LocalDate

object CycleCalculator {

    /**
     * Вычисляет следующую дату для повторяющейся задачи/подписки/ДР.
     * Корректно обрабатывает високосные годы и разную длину месяцев (например, 31 января -> 28 февраля).
     */
    fun calculateNextDateEpochDay(
        currentEpochDay: Long,
        rule: RepeatRule,
        interval: Int
    ): Long {
        val current = LocalDate.ofEpochDay(currentEpochDay)
        val safeInterval = interval.coerceAtLeast(1).toLong()

        val nextDate = when (rule) {
            RepeatRule.NONE -> current
            RepeatRule.DAILY -> current.plusDays(safeInterval)
            RepeatRule.WEEKLY -> current.plusWeeks(safeInterval)
            RepeatRule.MONTHLY -> current.plusMonths(safeInterval)
            RepeatRule.YEARLY -> current.plusYears(safeInterval)
        }
        return nextDate.toEpochDay()
    }
}