package ru.spiridonov.grafin.domain.entity

data class Question(
    val id: Int,
    val question: String,
    val answers: List<String>,
    val rightAnswer: Int
)