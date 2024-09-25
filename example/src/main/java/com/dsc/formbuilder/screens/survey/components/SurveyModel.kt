package com.dsc.formbuilder.screens.survey.components

data class SurveyModel(
    // First page
    val email: String,
    val card: String,
    val date: String,

    // Second page
    val ide: List<String>,
    val platform: List<String>,
    val language: List<String>,

    // Third page
    val os: String,
    val gender: String,
    val experience: String,
)
