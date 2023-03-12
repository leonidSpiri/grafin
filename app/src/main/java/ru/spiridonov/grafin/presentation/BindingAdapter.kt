package ru.spiridonov.grafin.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ru.spiridonov.grafin.R
import ru.spiridonov.grafin.domain.entity.GameResult
import ru.spiridonov.grafin.domain.entity.Question

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        count
    )
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        count
    )
}

@BindingAdapter("scoreAnswers")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.progress_answers),
        count
    )
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_answers),
        getPercentOfRightAnswers(gameResult)
    )
}

@BindingAdapter("emojiResult")
fun bindEmojiResult(imageView: ImageView, winner: Boolean) {
    imageView.setImageResource(getSmileResId(winner))
}

@BindingAdapter("pbSetProgress")
fun bindPbSetProgress(progressBar: ProgressBar, count: Int) {
    progressBar.setProgress(count, true)
}

@BindingAdapter("pbSetMax")
fun bindPbSetMax(progressBar: ProgressBar, list: List<Question>) {
    progressBar.max = list.size
}

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, stateColor: Boolean) {
    textView.setTextColor(getColorByState(stateColor, textView.context))
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

private fun getSmileResId(winner: Boolean) = if (winner)
    R.drawable.ic_smile
else
    R.drawable.ic_sad

private fun getColorByState(goodState: Boolean, context: Context) = ContextCompat.getColor(
    context, if (goodState) android.R.color.holo_green_light
    else android.R.color.holo_red_light
)

