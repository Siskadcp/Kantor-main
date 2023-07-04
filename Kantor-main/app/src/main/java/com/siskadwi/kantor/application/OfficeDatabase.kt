package com.siskadwi.kantor.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.siskadwi.kantor.dao.OfficeDao
import com.siskadwi.kantor.model.Office


@Database(entities = [Office::class], version = 2, exportSchema = false)
    abstract class OfficeDatabase: RoomDatabase(){
        abstract fun officeDao():OfficeDao
        companion object{
            private var INSTANCE: OfficeDatabase? = null
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
