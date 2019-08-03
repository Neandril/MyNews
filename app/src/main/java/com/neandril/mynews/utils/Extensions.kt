@file:JvmName("ExtensionsUtils")

package com.neandril.mynews.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

fun Int.paddingZero(): String {
    return String.format("%02d", this)
}
