package com.pulseplan.app.data

import android.content.Context
import androidx.room.*

class Converters {
    @TypeConverter
    fun fromItemType(value: ItemType): String = value.name

    @TypeConverter
    fun toItemType(value: String): ItemType = ItemType.valueOf(value)

    @TypeConverter
    fun fromRepeatRule(value: RepeatRule): String = value.name

    @TypeConverter
    fun toRepeatRule(value: String): RepeatRule = RepeatRule.valueOf(value)
}

@Database(entities = [PlannerItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plannerDao(): PlannerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pulseplan_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}