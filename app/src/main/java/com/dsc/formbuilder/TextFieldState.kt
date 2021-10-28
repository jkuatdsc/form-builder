package com.dsc.formbuilder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

open class TextFieldState (

    // add the validators implementation here...
    private val validator : (String) -> Boolean = {true}, 
    private val errorMessage: (String) -> String 
){
var text by mutableStateOf(" ")
}
fun validate() {
error = if (validator(text)){
null
}else 
{
var error by mutableStateOf<String?>(null)
errorMessage(text)
}
}