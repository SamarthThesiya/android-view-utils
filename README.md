# View Helper Libraries For Android

[![](https://jitpack.io/v/SamarthThesiya/android-view-utils.svg)](https://jitpack.io/#SamarthThesiya/android-view-utils)

<div align="center">
  <sub>Built with ❤︎ by
  <a href="https://github.com/SamarthThesiya">Samarth Thesiya</a>
</div>
<br/>

Easy to use and configurable library, **Which helps developer for rapid development**. Some task are so tedious in android development. Like validate content of EditText, Create RecyclerView, etc.

Here you can do some amazing stuff like validate EditText without single if condition, create recycler-view without adopter, etc.

# Features:
* Use built in validation like Required, Minimun length, Match regex, etc.
* If you want own validations then you can create your also.
* To implement a recycler-view, you just need to create only xml file for List Item.

# Installation
1. Gradle dependency (Project Level):
    ```
        allprojects {
            repositories {
                    jcenter()

                    //Add this line
                    maven { url 'https://jitpack.io' }
            }
        }
    ```
2. Gradle dependency (Module Level):
    ```
        dependencies {

            // For latest version check out here: https://jitpack.io/#SamarthThesiya/android-view-utils
            implementation 'com.github.SamarthThesiya:android-view-utils:[Version]'
        }
    ```
# Usage
## Use built in validations
1. activity_main.xml
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="com.example.viewutilsjava.MainActivity">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <customView.validatable.VuEditText
                android:id="@+id/et_vu_edit_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"/>

            <customView.dependantHolders.VuButton
                android:id="@+id/btn"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Xyz"/>

        </LinearLayout>

    </LinearLayout>
    ```
2. MainActivity
    ### Use Required Validation
    #### Java
    1. Fetch EditText and Button in VuEditText and VuButton respectively.
        ```
        VuEditText vuEditText = findViewById(R.id.et_vu_edit_text);
        VuButton   vuButton   = findViewById(R.id.btn);
        ```
    2. Add validation to the vuEditText
        ```
        vuEditText.validations().add(VuValidationMethods.REQUIRED);
        ```
        Add validation with custom message (Optional)
        ```
        vuEditText.validations().add(VuValidationMethods.REQUIRED, null, "Hey, It cannot be blank");
        ```
    3. Give vuEditText to vuButton. So that vuButton can validate vuEditText on it's click
        ```
        vuButton.dependantHandler.setValidatables(vuEditText)
        ```
        If you want to validat more than one VuEditText then you can pass n number of VuEditText in setValidatables()
        ```
        vuButton.dependantHandler.setValidatables(vuEditText1, vuEditText2, ..., vuEditTextn)
        ```
    4. Set onClickListener() on vuButton
        ```
        vuButton.setOnClickListener(this);
        ```
        That's it. Now vuButton's onClick() will be only clicked if all validations are succeed

    #### Kotlin
    ##### Note:
    * All the customization which are available for JAVA is available for Kotlin also.
    * Here is just kotlin's snippet. Detailed description is given for JAVA.
        ```
        val vuEditText: VuEditText = findViewById(R.id.et_vu_edit_text)
        val vuBtn: VuButton = findViewById(R.id.btn)

        vuEditText.validations().add(VuValidationMethods.REQUIRED)

        vuBtn.dependantHandler.setValidatables(vuEditText)
        vuBtn.setOnClickListener(this)
        ```
    ### Use Minimun Length Validation
    #### Java
    ```
    VuEditText vuEditText = findViewById(R.id.et_vu_edit_text);
    VuButton   vuButton   = findViewById(R.id.btn);

    vuEditText.validations().add(VuValidationMethods.MIN_LENGTH, 6);
    // OR
    vuEditText.validations().add(VuValidationMethods.MIN_LENGTH, 6, "Min 6 characters required");

    vuButton.dependantHandler.setValidatables(vuEditText)
    ```
    #### Kotlin
    ```
    val vuEditText: VuEditText = findViewById(R.id.et_name)
    val vuBtn: VuButton = findViewById(R.id.btn)

    vuEditText.validations().add(VuValidationMethods.MIN_LENGTH, 6)
    //OR
    vuEditText.validations().add(VuValidationMethods.MIN_LENGTH, 6, "Min 6 characters required")

    vuBtn.dependantHandler.setValidatables(vuEditText)
    vuBtn.setOnClickListener(this)
    ```
    ### Use Regex Validation
    #### Java
    ```
    VuEditText vuEditText = findViewById(R.id.et_vu_edit_text);
    VuButton   vuButton   = findViewById(R.id.btn);

    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{0,}$";
    vuEditText.validations().add(VuValidationMethods.REGEX, regex);
    // OR
    vuEditText.validations().add(VuValidationMethods.REGEX, regex, "Regex doesn't match");

    vuButton.dependantHandler.setValidatables(vuEditText)
    ```
    #### Kotlin
    ```
    val vuEditText: VuEditText = findViewById(R.id.et_name)
    val vuBtn: VuButton = findViewById(R.id.btn)

    String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{0,}$";
    vuEditText.validations().add(VuValidationMethods.REGEX, regex)
    // OR
    vuEditText.validations().add(VuValidationMethods.REGEX, regex, "Regex doesn't match")

    vuButton.dependantHandler.setValidatables(vuEditText)
    ```
## Use Your Own Validation
### We will create own simple validation to validate that string should start with '+'.
##### 1. Create a class as below. Let's give it name "MyValidators".
* JAVA
    ```
    public class MyValidators extends VuValidationMethods {

        // Give name to your validation (You can add multiple validations to this class)
        public static final String START_WITH_PLUS = "startWithPlus";

        // Make sure that function name is same as your validator value
        public boolean startWithPlus(Validations.Validation validation) {

            // First check that text is not empty
            if (TextUtils.isEmpty(vuValidatable.getText())) {
                validation.setMessage("Required");
                return false; // Validation failed
            }

            if (vuValidatable.getText().toString().toCharArray()[0] != '+') {
                // If custom message is not provided.
                if (TextUtils.isEmpty(validation.getMessage())) {

                    // Set default message
                    validation.setMessage("Should start with '+'");
                    return false; // Validation failed
                }
            }
            return true; // Validation success
        }
    }
    ```
* Kotlin
    ```
    class MyValidators: VuValidationMethods() {
        companion object {
            // Give name to your validation (You can add multiple validations to this class)
            val START_WITH_PLUS: String = "startWithPlus"
        }

        // Make sure that function name is same as your validator value
        fun startWithPlus(validation: Validations.Validation) : Boolean {

            // First check that text is not empty
            if (TextUtils.isEmpty(vuValidatable.text)) {
                validation.message = "Required"
                return false // Validation failed
            }

            if (vuValidatable.text.get(0) != '+') {
                // If custom message is not provided.
                if (TextUtils.isEmpty(validation.message)) {
                    // Set default message
                    validation.message = "Should start with '+'"
                }
                return false // Validation failed
            }
            return true // Validation success
        }
    }
    ```
2. MainActiviy
* JAVA
    ```
    VuEditText vuEditText = findViewById(R.id.et_vu_edit_text);
    VuButton   vuButton   = findViewById(R.id.btn);

    vuEditText.validations().add(MyValidators.START_WITH_PLUS);
    // OR
    vuEditText.validations().add(MyValidators.START_WITH_PLUS, null, "Invalid")

    // Don't forgot to setCustomValidatableMethods(), in case of custom validations
    vuButton.dependantHandler.setValidatables(vuEditText).setCustomValidatableMethods(new MyValidators());
    ```
* Kotlin
    ```
    val vuEditText: VuEditText = findViewById(R.id.et_name)
    val vuBtn: VuButton = findViewById(R.id.btn)

    vuEditText.validations().add(VuValidationMethods.START_WITH_PLUS, regex)
    // OR
    vuEditText.validations().add(VuValidationMethods.START_WITH_PLUS, null, "Invalid")

     // Don't forgot to setCustomValidatableMethods(), in case of custom validations
    vuButton.dependantHandler.setValidatables(vuEditText).setCustomValidatableMethods(MyValidators())
    ```
## Pro Tip
### You can apply validator on single VuEditText also
```
VuEditText vuEditText = findViewById(R.id.et_vu_edit_text);
VuButton   vuButton   = findViewById(R.id.btn);

vuEditText.validations().add(MyValidators.REQUIRED); // First this validation must be succed
vuEditText.validations().add(MyValidators.START_WITH_PLUS); // This validation will only execute if above are validated successfully

vuButton.dependantHandler.setValidatables(vuEditText).setCustomValidatableMethods(new MyValidators());
```
### Second argument of add() can be accessed in custom validator
* Suppose your custom validator requires some extra param(s). In such cases you can pass extra param in second argument of add()
    ```
    vuEditText.validations().add(MyValidators.MAX_LENGTH, 20);
    //OR
    vuEditText.validations().add(MyValidators.MAX_LENGTH, 20, "Max 20 characters only");
    ```
* This second parameter will be available in your custom validation method as below
    ```
    public Boolean maxLength(Validations.Validation validation) {
        if (validation.getExtra() == null) {
            throw new InvalidArgumentException("Need to pass maximum length of string while adding the validation");
        } else if (!(validation.getExtra() instanceof Integer)) {
            throw new InvalidArgumentException("For maxLength, extra should be positive number only");
        }

        // That second argument of add() will be received in validation.getExtra();
        int maxLength = (int) validation.getExtra();

        if (vuValidatable.getText().length() > maxLength) {
            if (TextUtils.isEmpty(validation.getMessage())) {
                validation.setMessage("Too many characters");
            }
            return false;
        }
        return true;
    }
    ```
## Implement Recycler View Without Adaptor
 1. If you want to use data-binding in recycle-view's item then enable data-binding in app level gradle
    ```
        android {
            buildFeatures {
                dataBinding true
            }
        }
    ```
2. Create recycler-view's model to store data. Let's say it "**ItemView**"
    ```
    public class ItemView {

        String title, subTitle, description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
    ```
3. Create recycler-view's layout. Let's say it "**item_view.xml**"
* With DataBiding
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <layout>
        <data>
            <variable
                name="model"
                type="models.databinding.ItemView" />
        </data>
        <LinearLayout
            xmlns:android="http://schemas.android.com/apk/res/android"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@{model.title}"
                android:textSize="25sp"
                android:layout_margin="10dp"
                android:textStyle="bold"
                android:textColor="#000000"/>

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@{model.subTitle}"
                android:textSize="25sp"
                android:layout_marginTop="-10dp"
                android:layout_marginEnd="10dp"
                android:layout_marginStart="10dp"
                android:textStyle="bold"/>

            <TextView
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:text="@{model.description}"
                android:textSize="18sp"
                android:layout_marginEnd="10dp"
                android:layout_marginStart="10dp"/>

        </LinearLayout>
    </layout>
    ```
* Without DataBinding
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <TextView
            android:id="@+id/tv_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Title"
            android:textSize="25sp"
            android:layout_margin="10dp"
            android:textStyle="bold"
            android:textColor="#000000"/>

        <TextView
            android:id="@+id/tv_subTitle"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Sub Title"
            android:textSize="25sp"
            android:layout_marginTop="-10dp"
            android:layout_marginEnd="10dp"
            android:layout_marginStart="10dp"
            android:textStyle="bold"/>

        <TextView
            android:id="@+id/tv_description"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Description"
            android:textSize="18sp"
            android:layout_marginEnd="10dp"
            android:layout_marginStart="10dp"/>

    </LinearLayout>
    ```
4. activity_main.xml
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="com.example.viewutilsjava.MainActivity">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <customView.VuRecyclerView
                android:id="@+id/vu_recycler_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"/>

        </LinearLayout>

    </LinearLayout>
    ```
5. MainActivity.java
* Create a function to prepare the data
    ```
    public ArrayList<ItemVuRecyclerView> getItems() {
        ArrayList<ItemVuRecyclerView> items = new ArrayList<>(5);
        for (int i = 0; i < 10; i++) {
            ItemVuRecyclerView itemVuRecyclerView = new ItemVuRecyclerView();
            itemVuRecyclerView.setTitle("Title " + (i+1));
            itemVuRecyclerView.setSubTitle("Sub Title " + (i+1));
            itemVuRecyclerView.setDescription("Description " + (i+1));

            items.add(itemVuRecyclerView);
        }

        return items;
    }
    ```
* Get VuRecyclerView
    ```
    VuRecyclerView vuRecyclerView = findViewById(R.id.vu_recycler_view);
    ```
* Populate RecyclerView (Using DataBinding)
    ```
    final ArrayList<ItemVuRecyclerView> items = getItems();
    vuRecyclerView.setData(R.layout.item_view, items.size(), this, new RecyclerViewListener() {
        @Override
        public void onBindViewHolder(@NonNull VuRecyclerView.VuRecyclerViewAdaptor.VuViewHolder holder, int position) {
            ItemViewBinding binding = (ItemViewBinding) holder.binding;
            binding.setModel(items.get(position));
        }
    });
    ```
* Populate RecyclerView (Without DataBinding)
    ```
    final ArrayList<ItemVuRecyclerView> items = getItems();
    vuRecyclerView.setData(R.layout.item_view, items.size(), this, new RecyclerViewListener() {
        @Override
        public void onBindViewHolder(@NonNull VuRecyclerView.VuRecyclerViewAdaptor.VuViewHolder holder, int position) {
            TextView tvTitle = holder.itemView.findViewById(R.id.tv_title);
            TextView tvSubTitle = holder.itemView.findViewById(R.id.tv_subTitle);
            TextView tvDescription = holder.itemView.findViewById(R.id.tv_description);

            tvTitle.setText(items.get(position).getTitle());
            tvSubTitle.setText(items.get(position).getSubTitle());
            tvDescription.setText(items.get(position).getDescription());
        }
    });
    ```