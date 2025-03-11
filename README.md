# Interactive Text Maker

![Header Image](https://github.com/emirhankolver/InteractiveTextMaker/blob/master/screenshots/sc2.png?raw=true)

## Overview
Interactive Text Maker allows you to create clickable and translatable texts in a `TextView` with ease. By marking special words with separator characters like `"__"`, users can tap on them, triggering a callback function that returns the index of the clicked word. This ensures:
- Seamless translation while maintaining the structure of tactile words across languages.
- Easy implementation of interactive text features without complex span management.

[Medium Article](https://medium.com/p/2b70e2072453)

---

## 🔄 Update Notice
If you previously used my dependency under the username `alonew0lfxx`, please update your `build.gradle` file to reflect my new username `emirhankolver`:

```gradle
dependencies {
    // OLD: implementation 'com.github.alonew0lfxx:InteractiveTextMaker:1.0.8'
    implementation 'com.github.emirhankolver:InteractiveTextMaker:1.0.8'
}
```

---

## 🚀 Installation
### Step 1: Add JitPack Repository
Ensure you have JitPack in your `build.gradle` (Project-level):

```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

### Step 2: Add the Dependency
Add the InteractiveTextMaker dependency in your `build.gradle` (App-level):

```gradle
dependencies {
    implementation 'com.github.emirhankolver:InteractiveTextMaker:1.0.8'
}
```

---

## ✨ New Feature: InteractiveTextView
### 🖥️ Real-Time Preview of Your Special Words
You can now use `InteractiveTextView` to directly display and preview clickable words in XML without additional setup.

#### XML Usage:
```xml
<com.emirhankolver.itm.InteractiveTextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textSize="16sp"
    android:fontFamily="@font/bold"
    app:specialTextUnderlined="true"
    android:text="How To __Make__ Clickable __Text__ In A __TextView__?" />
```

### InteractiveTextView Output:
![App Screenshot](https://github.com/emirhankolver/InteractiveTextMaker/blob/master/screenshots/sc3.png?raw=true)

---

## 🛠 Example Usage of InteractiveTextMaker
You can implement InteractiveTextMaker in Kotlin to add interactivity to `TextView` dynamically.

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
                    2 -> Toast.makeText(this, "'TextView' has been clicked", Toast.LENGTH_SHORT).show()
                }
            }
            .initialize()

        setContentView(binding.root)
    }
}
```

### InteractiveTextMaker Output:
![App Screenshot](https://github.com/emirhankolver/InteractiveTextMaker/blob/master/screenshots/sc1.png?raw=true)

---

## 📜 License
This project is licensed under the [MIT License](https://choosealicense.com/licenses/mit/).

---

### 💡 Contributions & Feedback
Feel free to open an issue or contribute via pull requests!
Happy coding! 🚀
