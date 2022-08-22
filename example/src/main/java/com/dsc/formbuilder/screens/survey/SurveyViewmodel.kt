package com.dsc.formbuilder.screens.survey

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SurveyViewmodel: ViewModel(){

    private val _screen: MutableState<Int> = mutableStateOf(0)
    val screen: State<Int> = _screen

    fun navigate(screen: Int){
        _screen.value = screen
    }
}