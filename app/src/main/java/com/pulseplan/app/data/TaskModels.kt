package com.pulseplan.app.data

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ItemType {
    TASK,         // Обычная задача
    EVENT,        // Встреча / дело на конкретное время
    SUBSCRIPTION, // Подписка / регулярный платеж
    BIRTHDAY,     // День рождения / памятная дата
    NOTE          // Заметка (общая или привязанная к дате)
}

enum class RepeatRule {
    NONE,    // Разовая
    DAILY,   // Каждые N дней
    WEEKLY,  // Каждые N недель
    MONTHLY, // Каждые N месяцев
    YEARLY   // Каждые N лет
}

@Entity(tableName = "planner_items")
data class PlannerItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val type: ItemType = ItemType.TASK,

    // Дата события в формате EpochDay (LocalDate.toEpochDay()), null для непривязанных заметок
    val dateEpochDay: Long? = null,
    // Предыдущая дата цикла (нужна для кнопки "Отменить", если случайно нажал "Выполнено")
    val previousDateEpochDay: Long? = null,

    // Время события в расписании (например, 14:30). Если null — задача на весь день
    val startHour: Int? = null,
    val startMinute: Int? = null,
    val durationMinutes: Int = 60,

    // Настройки повторения
    val repeatRule: RepeatRule = RepeatRule.NONE,
    val repeatInterval: Int = 1,

    // Предварительные напоминания: список дней ДО даты события через запятую.
    // Например: "7,3,1,0" = напомнить за 7 дней, за 3 дня, за 1 день и в день события.
    val remindDaysBeforeCsv: String = "0",
    // В какое время дня присылать напоминания (по умолчанию в 10:00)
    val reminderHour: Int = 10,
    val reminderMinute: Int = 0,

    // Дополнительно для подписок и заметок
    val amountText: String = "", // Например: "399 ₽"
    val isPinned: Boolean = false,
    val isCompleted: Boolean = false,
    val createdAtMillis: Long = System.currentTimeMillis()
) {
    fun getRemindDaysList(): List<Int> {
        if (remindDaysBeforeCsv.isBlank()) return emptyList()
        return remindDaysBeforeCsv.split(",")
            .mapNotNull { it.trim().toIntOrNull() }
            .distinct()
            .sortedDescending()
    }
}