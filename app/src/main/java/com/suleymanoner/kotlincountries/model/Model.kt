package com.suleymanoner.kotlincountries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Country(
    @ColumnInfo(name = "name")
    @SerializedName(value = "name")
    val countryName: String?,

    @ColumnInfo(name = "region")
    @SerializedName(value = "region")
    val countryRegion: String?,

    @ColumnInfo(name = "capital")
    @SerializedName(value = "capital")
    val countryCapital: String?,

    @ColumnInfo(name = "currency")
    @SerializedName(value = "currency")
    val countryCurrency: String?,

    @ColumnInfo(name = "language")
    @SerializedName(value = "language")
    val countryLanguage: String?,

    @ColumnInfo(name = "flag")
    @SerializedName(value = "flag")
    val imageUrl: String?)

{
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}