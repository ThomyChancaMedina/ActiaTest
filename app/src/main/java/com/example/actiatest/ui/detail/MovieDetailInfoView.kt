package com.example.actiatest.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.example.actiatest.model.database.Movie

class MovieDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setMovie(movie: Movie) = movie.apply {
        text = buildSpannedString {

            bold { append("Director: ") }
            appendLine(originalLanguage)

            bold { append("Release date: ") }
            appendLine(releaseDate)

            bold { append("Duration: ") }
            appendLine(popularity.toString())

            bold { append("Score: ") }
            append(voteAverage.toString())
        }
    }
}