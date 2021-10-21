## Jetpack Compose FormBuilder
A highly android library used to provide an abstraction layer over form elements as well as provide a DRY code implementation of a form.

- [Introduction](#introduction) 
- [Features](#features)
- [Usage](#usage)

### Introduction
The library aims to provide a dynamic way to build a `Form` using Jetpack compose input fields. There are common actions that are performed with forms such as validation and management of data. 

With the current version of Jetpack compose, we don't have a `Form` composable which leads to a lot of redundant code that can get messy really quick. Assume you have 10 text fields, you have to manage the state and validation of each field individually. This will lead to a lot of variables and if/when expressions in that single composable. 

### Features
The library approaches the problem as follows:

*1. Form composable*

We have a `Form` composable that is in charge of drawing all the input fields on the screen. It has its own state used to validate the fields it contains and returns the data from the form. 
The data returned from the form is in the form of `Map<String, Any>` where the fields' names are the keys in the map.

*2. Validators*

This is a group of methods that perform an actual check on the field's data. They are used to check if the data in the field matches the specified criteria.
Available validators are:
* *Required*: this requires that a field should not be empty
* *Email*: this requires the field value to match the Email regex
* *Min*: this receives an argument which the value should be greater than
* *Max*: this receives an argument which the value should not exceed or should be less than

*3. Input fields*

There should be a field composable that accepts the following attributes:
* *name*: this will be used as the key in the form's result/data
* *validators*: this is a list containing all possible validators to be performed on that field
* *styles*: theses are a couple of attributes available on the normal Material TextField

Once you call validate from the `Form`'s state, all the form fields must be validated and the method should return a boolean to denote whether all the validators have passed or any has failed.
The form state should also have a method to access all the data from each of the fields in the `Form`.

### Usage
A simple form element should have one state. It should receive a list of fields each with their own attributes.

```kotlin
@Composable
fun MyForm(){
    val formState by remember { FormBuilderState() }
    
    FormBuilder(
        state = state,
        fields = [
            FormField(
                name = "email",
                validators = [Required, Email]
            ),
            FormField(
                name = "age",
                validators = [Required, Min(18)]
            )
        ]
    )
    
    // to validate
    if (state.validate()){
        val data = state.values() // {email=test@mail.com, age=13}
    }
}
```

MIT [Licence](LICENSE)