package com.example.interactivetextmaker

import android.content.Context
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils

class InteractiveTextMaker private constructor(
    private val textView: TextView,
    private val context: Context,
) {

    private var specialWordSeparator: String = "__"
    private var onTextClicked: ((index: Int) -> Unit) = {}
    private var specialTextColor: Int = textView.currentTextColor
    private var specialTextHighLightColor: Int = ColorUtils.setAlphaComponent(specialTextColor, 50)
    private var specialTextFontFamily = textView.typeface
    private var specialTextSize = textView.textSize
    private var underlinedSpecialText: Boolean = false

    fun setSpecialTextSeparator(separator: String): InteractiveTextMaker {
        specialWordSeparator = separator
        return this
    }

    fun setSpecialTextSize(size: Float): InteractiveTextMaker {
        specialTextSize = size
        return this
    }

    fun setSpecialTextFontFamily(@FontRes fontId: Int): InteractiveTextMaker {
        specialTextFontFamily = ResourcesCompat.getFont(context, fontId)!!
        return this
    }

    fun setSpecialTextUnderLined(boolean: Boolean): InteractiveTextMaker {
        underlinedSpecialText = boolean
        return this
    }

    fun setSpecialTextColor(@ColorRes colorId: Int): InteractiveTextMaker {
        val color = ContextCompat.getColor(context, colorId)
        specialTextColor = color
        specialTextHighLightColor = ColorUtils.setAlphaComponent(color, 50)
        return this
    }

    fun setSpecialTextHighlightColor(@ColorRes color: Int): InteractiveTextMaker {
        specialTextHighLightColor = ContextCompat.getColor(context, color)
        return this
    }

    fun setOnTextClickListener(func: (index: Int) -> Unit): InteractiveTextMaker {
        onTextClicked = func
        return this
    }

    fun initialize() {
        val regex = Regex("$specialWordSeparator(.*?)$specialWordSeparator")
        val words = regex.findAll(textView.text)
        val span = SpannableString(textView.text.replace(Regex(specialWordSeparator), ""))
        val actionTextLength = specialWordSeparator.length * 2// A word result has twice of it
        if (words.toList().isEmpty()) {
            Log.w(
                TAG,
                "initialize: WARNING No results has found with separator:'$specialWordSeparator'"
            )
            return
        }
        words.forEachIndexed { index: Int, wordResult: MatchResult ->
            val startIndex = wordResult.range.first - (actionTextLength * index)
            val endIndex = wordResult.range.last - (actionTextLength * (index + 1)) + 1
            span.setSpan(
                object : ClickableSpan() {
                    override fun onClick(p0: View) {
                        onTextClicked(index)
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.isUnderlineText = underlinedSpecialText
                        ds.textSize = specialTextSize
                        ds.typeface = specialTextFontFamily
                        ds.color = specialTextColor
                    }
                },
                startIndex,
                endIndex,
                0
            )
            textView.linksClickable = true
            textView.isClickable = true
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.text = span
            textView.highlightColor = specialTextHighLightColor
        }
    }


    companion object {
        fun of(textView: TextView): InteractiveTextMaker =
            InteractiveTextMaker(textView, textView.context)

        // Will be used at debugging.
        private const val TAG = "InteractiveTextMaker"
    }
}