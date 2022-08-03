package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.interactivetextmaker.InteractiveTextMaker
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.textView.text = "__Welcome__ to Interactive __Text__ Maker!\nHave a __nice__ day!"
        InteractiveTextMaker.of(binding.textView)
            .setSpecialTextColor(R.color.purple_500)
            .setSpecialTextFontFamily(R.font.bold_italic)
            .setSpecialTextUnderLined(true)
            .setOnTextClickListener {
                when (it) {
                    0 -> Toast.makeText(this, "'Make' has been clicked", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(this, "'Text' has been clicked", Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this, "'TextView' has been clicked", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .initialize()
        setContentView(binding.root)
    }
}