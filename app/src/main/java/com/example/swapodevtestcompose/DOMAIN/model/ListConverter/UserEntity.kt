package com.example.swapidevtest.DOMAIN.model.ListConverter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "users"
)
data class UserEntity(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address_lines") var addressLines: ArrayList<String>,
    @PrimaryKey val id: Int = 0
)