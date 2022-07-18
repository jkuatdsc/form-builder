[![](https://jitpack.io/v/jkuatdsc/form-builder.svg)](https://jitpack.io/#jkuatdsc/form-builder)

## Jetpack Compose FormBuilder

A customisable android library used to provide an abstraction layer over form elements as well as provide a DRY code
implementation of a form.

The library is used to help in the state management of a `Form` in Jetpack compose. Currently, we don't have an official
support for a form state so the library is used to provide a custom implementation of the same.

### <a id="installs" href="#installs">Installation</a>

In the root `build.gradle` file add the following:

```groovy
repositories {
    maven { url "https://jitpack.io" }
}
```

Then add the following to your module's `build.gradle`

```groovy
dependencies {
    implementation 'com.github.jkuatdsc:form-builder:${version}'
}
```

### <a id="basics" href="#basics">Basic Usage</a>

The library provides a [`FormState`](/form-builder/src/main/java/com/dsc/form_builder/FormState.kt) class that
represents the state of your Form. It receives a list of field classes that inherit the basic properties from the
[`BaseState`](/form-builder/src/main/java/com/dsc/form_builder/BaseState.kt) class.

```kotlin
val formState = FormState(
    fields = listOf(
        TextFieldState(
            name = "email",
            transform = { it.trim().lowercase() },
            validators = listOf(Validators.Email()),
        ),
        ChoiceState(
            name = "gender",
            validators = listOf(Validators.Required())
        ),
        SelectState(
            name = "hobbies",
            validators = listOf(Validators.Required())
        )
    )
)
```

You can pass a list of [Validators](/form-builder/src/main/java/com/dsc/form_builder/Validators.kt) to the
field states. These validators are used to check the validity of the input in the specific field. The validators are
executed in the order they are passed.

The transform function allows you to change the text field value to whatever class you desire, e.g, `TextFieldState<String>` to `TextFieldState<Int>`.

In your UI:

```kotlin
val formState = remember { viewmodel.formState }

val emailState: TextFieldState = formState.getState("email")

OutlinedTextField(
    value = emailState.text,
    isError = emailState.hasError,
    label = { Text("Email address") },
    onValueChange = { emailState.change(it) }
)
if (emailState.hasError) Text(emailState.errorMessage, color = Color.Red)
```

We can get individual states for the fields using the `getState` function in the FormState class. We can then access
various properties of the state like `text`, `hasError`, `errorMessage` etc.

> Don't forget to update your state using the setter functions in each field state.

To validate your form:

```kotlin
if (formState.validate()) {
    val data = formState.getData(Credentials::class)
    Log.d("Data", "submit: data from the form $data")
}
```

The `validate` function returns `true` if all the fields are valid. You can then access data from the form using
the `getData` function. Pass in your data class and using reflection, we convert the map (`Map<String, Any>`) to your
data class.

You can also pass in custom error messages to the validators.

```kotlin
TextFieldState(
    name = "age",
    transform = { it.toInt() },
    validators = listOf(Validators.MinValue(limit = 18, message = "too young"))
)
```

For more details about the library, check out the [references](https://jkuatdsc.github.io/form-builder/).

### <a id="links" href="#links">Further Reading</a>

The links below provide a reinforced understanding to the library.
* [Introduction to Form Builder basics](https://www.section.io/engineering-education/jetpack-compose-forms/)
* [Advanced Form Builder operations guide](https://www.section.io/engineering-education/making-jetpack-form-builder/)

MIT [Licence](LICENSE)
