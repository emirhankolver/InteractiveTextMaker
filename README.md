# Interactive Text Maker

![Header Image](https://github.com/Alonew0lfxx/InteractiveTextMaker/blob/master/screenshots/sc2.png?raw=true)

The goal of this project is to make texts in TextView clickable and also translatable easily.

User separates each special word with separator characters like `"__"`
and then when tapping all the custom words inside the TextView The `.setOnTextClickListener`(InteractiveTextMaker) or 
`addOnSpecialWordClickListener`(InteractiveTextView) function is called with the index of the touched word. 
In this way, sentences can be translated into other languages more easily and The locations of tactile sentences
remain fixed in all languages.

Medium Article: https://medium.com/p/2b70e2072453

## Installation

You can copy the **InteractiveTextMaker** class or add as a dependency to your project.

Step 1. Add the JitPack repository to your build file

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add The InteractiveTextMaker Dependency

```gradle
dependencies {
    implementation 'com.github.Alonew0lfxx:InteractiveTextMaker:1.0.3'
}
```


## NEW! InteractiveTextView
##### You can also see the preview of your text with your special words processed.

```xml
<com.alonew0lfxx.interactivetextmaker.InteractiveTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textSize="16sp"
        android:fontFamily="@font/bold"
        app:specialTextUnderlined="true"
        android:text="How To __Make__ Clickable __Text__ In A __TextView__?" />
```

## InteractiveTextView Result
![App Screenshot](https://github.com/Alonew0lfxx/InteractiveTextMaker/blob/master/screenshots/sc3.png?raw=true)


## Example Usage of InteractiveTextMaker

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.textView.text = "How To __Make__ Clickable __Text__ In A __TextView__?"
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
```

## InteractiveTextMaker Result
![App Screenshot](https://github.com/Alonew0lfxx/InteractiveTextMaker/blob/master/screenshots/sc1.png?raw=true)

## License

[MIT](https://choosealicense.com/licenses/mit/)

