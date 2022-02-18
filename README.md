[![](https://jitpack.io/v/jkuatdsc/form-builder.svg)](https://jitpack.io/#jkuatdsc/form-builder)
[![](https://jitpack.io/v/jkuatdsc/form-builder/badge.svg)](https://jitpack.io/#jkuatdsc/form-builder)

## Jetpack Compose FormBuilder
A customisable android library used to provide an abstraction layer over form elements as well as provide a DRY code implementation of a form.

The library is used to help in the state management of a `Form` in Jetpack compose. Currently, we don't have an official support for a Form so the library is used to provide a custom implementation of the same.

### Installation
In the root `build.gradle` file add the following:

    repositories {
        ...
        maven { url "https://jitpack.io" }
    }

Then add the following to your module's `build.gradle`

    dependencies {
        implementation 'com.github.jkuat.form-builder:form-builder:${version}'
    }

### Basic usage
The library provides a [`FormState`](FormState.kt) class that represents the state of your Form. It receives a list of [`TextField`](TextFieldState.kt)

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

### Features

The log:
```bash
com.dsc.formbuilder D/Data: submit: data from the form Credentials(email=test@mail.com, password=12345678, age=21)
```

MIT [Licence](LICENSE)