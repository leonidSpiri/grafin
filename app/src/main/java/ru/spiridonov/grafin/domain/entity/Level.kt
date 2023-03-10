package ru.spiridonov.grafin.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Level(
    val id: Int,
    val name: String,
    val description: String
):Parcelable