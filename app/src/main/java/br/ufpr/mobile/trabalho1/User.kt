package br.ufpr.mobile.trabalho1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userName: String,
    val rights: Int
): Parcelable