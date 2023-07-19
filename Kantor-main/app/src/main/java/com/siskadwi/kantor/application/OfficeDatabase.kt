package com.siskadwi.kantor.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.siskadwi.kantor.dao.OfficeDao
import com.siskadwi.kantor.model.Office


@Database(entities = [Office::class], version = 2, exportSchema = false)
    abstract class OfficeDatabase: RoomDatabase(){
        abstract fun officeDao():OfficeDao
        companion object{
            private var INSTANCE: OfficeDatabase? = null

            private val migration1to2 : Migration =object : Migration(1,2){
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE snack_table ADD COLUMN latitude Double DEFAULT 0.0")
                    database.execSQL("ALTER TABLE snack_table ADD COLUMN longitude Double DEFAULT 0.0")
                }
            }
            fun getDatabase(context: Context): OfficeDatabase{
                return INSTANCE ?: synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        OfficeDatabase::class.java,
                        "office_database"
                    )

                        .allowMainThreadQueries()
                        .build()
                    INSTANCE=instance
                    instance
                }
            }
        }
    }
