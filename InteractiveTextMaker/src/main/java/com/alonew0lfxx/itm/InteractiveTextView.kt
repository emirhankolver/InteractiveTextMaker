package com.alonew0lfxx.itm

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes

class InteractiveTextView : AppCompatTextView {

    private val listeners = mutableListOf<(index: Int) -> Unit>()
    private var attrs: AttributeSet? = null

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.attrs = attrs
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        this.attrs = attrs
    }

    @SuppressLint("SetTextI18n")
    fun initialize(context: Context, attrs: AttributeSet?) {
        context.withStyledAttributes(attrs, R.styleable.InteractiveTextView) {
            val colorPrimary = TypedValue().let {
                getContext().theme.resolveAttribute(android.R.attr.colorPrimary, it, true)
                it.data
            }
            val specialTextColor =
                getColor(R.styleable.InteractiveTextView_specialTextColor, colorPrimary)
            val specialTextHighlightColor =
                getColor(R.styleable.InteractiveTextView_specialTextHighlightColor, -1)
            val specialTextUnderlined =
                getBoolean(R.styleable.InteractiveTextView_specialTextUnderlined, false)
            val allTextUnderlined =
                getBoolean(R.styleable.InteractiveTextView_allTextUnderlined, false)
            val specialTextSize = getDimension(
                R.styleable.InteractiveTextView_specialTextSize,
                this@InteractiveTextView.textSize
            )
            val specialTextFontFamily = try {
                val fontId =
                    getResourceId(R.styleable.InteractiveTextView_specialTextFontFamily, -1)
                ResourcesCompat.getFont(context, fontId)
            } catch (e: Exception) {
                this@InteractiveTextView.typeface!!
            }
            val specialTextSeparator =
                getString(R.styleable.InteractiveTextView_specialTextSeparator) ?: "__"
            if (allTextUnderlined) text = "$specialTextSeparator${text}$specialTextSeparator"
            InteractiveTextMaker.of(this@InteractiveTextView)
                .also {
                    it.setSpecialTextColor(specialTextColor)
                    it.setSpecialTextSeparator(specialTextSeparator)
                    it.setSpecialTextUnderLined(specialTextUnderlined)
                    it.setSpecialTextSize(specialTextSize)
                    it.setSpecialTextFontFamily(specialTextFontFamily!!)
                    if (specialTextHighlightColor != -1)
                        it.setSpecialTextHighlightColor(specialTextHighlightColor)
                }
                .setOnTextClickListener { index -> listeners.forEach { it(index) } }
                .initialize()
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        initialize(context, attrs)
    }

    /**
     * Whenever a special word has been clicked this [function] will be run with index
     * of the word.
     */
    fun addOnSpecialWordClickListener(function: (index: Int) -> Unit) {
        listeners.add(function)
    }

}