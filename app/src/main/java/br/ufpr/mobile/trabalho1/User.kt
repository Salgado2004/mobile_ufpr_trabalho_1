package br.ufpr.mobile.trabalho1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userName: String,
    var rights: Int
): Parcelable