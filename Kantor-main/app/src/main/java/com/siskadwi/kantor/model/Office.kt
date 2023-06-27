package com.siskadwi.kantor.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.net.Inet4Address

@Parcelize
@Entity(tableName = "office-table")
data class Office(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 8,
    val name: String,
    val address: String
     ) : Parcelable