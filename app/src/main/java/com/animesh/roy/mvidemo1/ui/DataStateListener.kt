package com.animesh.roy.mvidemo1.ui

import com.animesh.roy.mvidemo1.util.DataState


interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}