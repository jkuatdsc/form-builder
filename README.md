[![](https://jitpack.io/v/jkuatdsc/form-builder.svg)](https://jitpack.io/#jkuatdsc/form-builder)
[![](https://jitpack.io/v/jkuatdsc/form-builder/badge.svg)](https://jitpack.io/#jkuatdsc/form-builder)

## Jetpack Compose FormBuilder

A customisable android library used to provide an abstraction layer over form elements as well as provide a DRY code
implementation of a form.

The library is used to help in the state management of a `Form` in Jetpack compose. Currently, we don't have an official
support for a Form so the library is used to provide a custom implementation of the same.

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
    implementation 'com.github.jkuatdsc.form-builder:form-builder:${version}'
}
```

### <a id="basics" href="#basics">Basic Usage</a>

The library provides a [`FormState`](/form-builder/src/main/java/com/dsc/form_builder/FormState.kt) class that
represents the state of your Form. It receives a list
of [`TextFieldState`](/form-builder/src/main/java/com/dsc/form_builder/TextFieldState.kt) classes. These TextFieldState
classes represent the state of individual TextFields.

```kotlin
val formState = FormState(
    fields = listOf(
        TextFieldState(
            name = "email",
            transform = { it.trim().lowercase() },
            validators = listOf(Validators.Email()),
        ),
        TextFieldState(
            name = "password",
            validators = listOf(Validators.Required())
        ),
    )
)
```

You can pass a list of [Validators](/form-builder/src/main/java/com/dsc/form_builder/Validators.kt) to the
TextFieldState. These validators are used to check the validity of the input in the specific field. The validators are
executed in the order they are passed.

The transform function allows you to change the text field value from `String` to whatever class you desire. This helps
in specifying the type of TextFieldState i.e `TextFieldState<String>` or `TextFieldState<Int>`.

In your UI:

```kotlin
val formState = remember { viewmodel.formState }

val emailState = formState.getState("email")
val passwordState = formState.getState("password")

OutlinedTextField(
    value = emailState.text,
    isError = emailState.hasError,
    label = { Text("Email address") },
    onValueChange = { emailState.change(it) }
)
if (emailState.hasError) Text(emailState.errorMessage, color = Color.Red)

Spacer(modifier = Modifier.height(20.dp))

OutlinedTextField(
    value = passwordState.text,
    isError = passwordState.hasError,
    label = { Text("Password") },
    onValueChange = { passwordState.change(it) }
)
if (passwordState.hasError) Text(passwordState.errorMessage, color = Color.Red)
```

We can get individual states for the fields using the `getState` function in the FormState class. We can then access
various properties of the state like `text`, `hasError`, `errorMessage` etc.

> Don't forget to update your state using the `change` function in the TextFieldState.

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

The log:

```bash
com.dsc.formbuilder D/Data: submit: data from the form Credentials(email=test@mail.com, password=12345678)
```

You can also pass in custom error messages to the validators.

```kotlin
TextFieldState(
    name = "age",
    transform = { it.toInt() },
    validators = listOf(Validators.MinValue(limit = 18, message = "too young"))
)
```

### <a id="textfield" href="#textfield">TextFieldState Properties</a>

#### Arguments

* `name` - The name of the field.
* `initial` - The initial text value of the field.
* `transform` - A function that transforms the text field value to the desired type.
* `validators` - A list of validators used to verify the validity of the input. Must be one of the [_Validators_](/form-builder/src/main/java/com/dsc/form_builder/Validators.kt)

#### Methods

| Method      | Return type | Description                                                                                                                                                                                     |
|-------------|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `change`    | Unit        | This method is used to update the state of the textfield. It also hides the error in case the user started typing again after a failed validation. It receives the update/string as an argument. |
| `validate`  | Boolean     | Goes through all the specified validators checking if the text in the class meets therequired specifications. If one returns false, then the function returns false.                              |
| `showError` | Unit        | This is used to update the error in the class. It sets `hasError` to true and the `errorMessage` to the specified error.                                                                         |
| `hideError` | Unit        | The opposite of `showError`. Clears the error message and sets `hasError` to false.                                                                                                             |

### <a id="form" href="#form">FormState Properties</a>

The class receives only one argument, `fields`. This is a list of TextFieldState objects.

| Method     | Return type    | Description                                                                                                                                                                                    |
|------------|----------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `validate` | Boolean        | This method goes through all the fields specified and calls the`.validate()` function. If any of the fields' validations return false, the methods returns false too. Otherwise, it returns true. |
| `getState` | TextFieldState | This is used to get an instance of the TextFieldState class using the name.                                                                                                                    |
| `getData`  | Any            | You can pass in a custom data class that will be used to map the form values to the properties of your data class. The transformation occurs at this point.                                    |

### <a id="links" href="#links">Further Reading</a>

The links below provide a reinforced understanding to the library.
* [Introduction to Form Builder basics](https://www.section.io/engineering-education/jetpack-compose-forms/)
* [Advanced Form Builder operations guide](https://www.section.io/engineering-education/making-jetpack-form-builder/)

MIT [Licence](LICENSE)
