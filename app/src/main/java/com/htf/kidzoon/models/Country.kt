package com.htf.kidzoon.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_country")
data class Country(
    var dial_code: String,
    var en_name: String,
    var flag: String,
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String
)