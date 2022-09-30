package com.alonew0lfxx.itm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.content.withStyledAttributes

class InteractiveTextView(
    context: Context,
    attrs: AttributeSet?
) : AppCompatTextView(context, attrs) {

    private val listeners = mutableListOf<(index: Int) -> Unit>()

    // Attributes.
    private var colorPrimary: Int? = 0
    private var specialTextColor: Int? = 0
    private var specialTextHighlightColor: Int? = 0
    private var specialTextUnderlined: Boolean? = false
    private var allTextUnderlined: Boolean? = false
    private var specialTextSize: Float? = 0f
    private var specialTextFontFamily: Typeface? = null
    private var specialTextSeparator: String? = null
    private lateinit var previousString: String


    init {
        Log.d(TAG, "null() called")
        context.withStyledAttributes(attrs, R.styleable.InteractiveTextView) {
            colorPrimary = TypedValue().let {
                getContext().theme.resolveAttribute(android.R.attr.colorPrimary, it, true)
                it.data
            }
            specialTextSeparator =
                getString(R.styleable.InteractiveTextView_specialTextSeparator) ?: "__"
            specialTextColor =
                getColor(R.styleable.InteractiveTextView_specialTextColor, colorPrimary!!)
            specialTextHighlightColor =
                getColor(R.styleable.InteractiveTextView_specialTextHighlightColor, -1)
            specialTextUnderlined =
                getBoolean(R.styleable.InteractiveTextView_specialTextUnderlined, false)
            allTextUnderlined =
                getBoolean(R.styleable.InteractiveTextView_allTextUnderlined, false)
            specialTextSize = getDimension(
                R.styleable.InteractiveTextView_specialTextSize,
                this@InteractiveTextView.textSize
            )
            specialTextFontFamily = try {
                val fontId =
                    getResourceId(R.styleable.InteractiveTextView_specialTextFontFamily, -1)
                ResourcesCompat.getFont(context, fontId)!!
            } catch (e: Exception) {
                this@InteractiveTextView.typeface!!
            }
            Log.d(TAG, "specialTextSeparator: $specialTextSeparator")
        }
        initialize(context)
    }


    /*
     * I don't how that's happening but I managed to fix the
     * ```
     * Parameter specified as non-null is null: method kotlin.jvm.internal.Intrinsics.checkNotNullParameter, parameter separator
     * ```
     * crash with null controls at initialize function. In somehow the variables like "specialTextSeparator" become null after
     * the setText() method runs.
     *
     */
    @SuppressLint("SetTextI18n")
    fun initialize(context: Context) {
        Log.d(TAG, "initialize() called with: $specialTextSeparator")
        if (allTextUnderlined == null) return
        if (specialTextColor == null) return
        if (specialTextSeparator == null) return
        if (specialTextUnderlined == null) return
        if (specialTextSize == null) return
        if (specialTextFontFamily == null) return
        if (specialTextHighlightColor == null) return
        if (allTextUnderlined!!) text = "$specialTextSeparator${text}$specialTextSeparator"
        InteractiveTextMaker.of(this@InteractiveTextView)
            .also {
                it.setSpecialTextColor(specialTextColor!!)
                it.setSpecialTextSeparator(specialTextSeparator!!)
                it.setSpecialTextUnderLined(specialTextUnderlined!!)
                it.setSpecialTextSize(specialTextSize!!)
                it.setSpecialTextFontFamily(specialTextFontFamily!!)
                if (specialTextHighlightColor != -1)
                    it.setSpecialTextHighlightColor(specialTextHighlightColor!!)
            }
            .setOnTextClickListener { index -> listeners.forEach { it(index) } }
            .initialize()
    }

    /*
     * InteractiveTextMaker uses .setText method to work. It'll gonna create a [setText] loop.
     * to block that I'm gonna use previousString variable to capture every string. and compare with
     * previous one.
     *
     * If anyone knows how to make this in right way please contact me :)
     */
    override fun setText(text: CharSequence?, type: BufferType?) {
        if (this::previousString.isInitialized && previousString == text) return
        previousString = text.toString()
        super.setText(text, type)
        initialize(context)
    }


    /**
     * Whenever a special word has been clicked this [function] will be run with index
     * of the word.
     */
    fun addOnSpecialWordClickListener(function: (index: Int) -> Unit) {
        listeners.add(function)
    }

    companion object {
        private const val TAG = "InteractiveTextView"
    }

}