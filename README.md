[![](https://jitpack.io/v/jkuatdsc/form-builder.svg)](https://jitpack.io/#jkuatdsc/form-builder)


## Jetpack Compose FormBuilder

A customisable android library used to provide an abstraction layer over form elements as well as provide a DRY code
implementation of a form.

The library is used to help in the state management of a `Form` in Jetpack compose. Currently, we don't have an official
support for a form state so the library is used to provide a custom implementation of the same.

 * [<a id="installs" href="#installs">Installation</a>](#installation)
 * [<a id="basics" href="#basics">Basic Usage</a>](#basic-usage)
 * [<a id="demos" href="#demos">Demos</a>](#demos)
 * [<a id="links" href="#links">Further Reading</a>](#further-reading)
 * [<a id="contribution" href="#contribution">Contribution Guideline</a>](#contribution-guideline)

<!-- TOC --><a name="installation"></a>
### <a id="installs" href="#installs">Installation</a>

In the root `build.gradle` file add the following:

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}
```

Then add the following to your module's `build.gradle`

```kotlin
dependencies {
    implementation("com.github.jkuatdsc:form-builder:${version}")
}
```

<!-- TOC --><a name="basic-usage"></a>
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
if (emailState.hasError) Text(emailState.errorMessage, color = MaterialTheme.colors.error)
```

We can get individual states for the fields using the `getState` function in the FormState class. We can then access
various properties of the state like `text`, `hasError`, `errorMessage` etc.

> Don't forget to update your state using the setter functions in each field state.

To validate your form and get form data:

```kotlin
if (formState.validate()) {
    val data = formState.getData(Credentials::class)
    Log.d("Data", "submit: data from the form $data")
}
```
Here is what the `Credentials` data class looks like. Take note of how the property names correspond to the field values 
passed when instantiating the form state.

```Kotlin
data class Credentials(
    val email: String,
    val gender: String,
    val hobbies: List<String>
)
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

<!-- TOC --><a name="demos"></a>
### <a id="demos" href="#demos">Demos</a>

**Demo 1** : In this demo, the validations are executed before the next screen is presented.

https://user-images.githubusercontent.com/47350130/204373150-86c89b3a-8ab2-4c52-8429-b778d0307845.mp4

**Demo 2** : In this demo, the validations are run at the end of the survey when submitting data.

https://user-images.githubusercontent.com/47350130/204372977-e8b66f71-0b61-4d93-a2c9-d8de04876785.mp4

You can find the sample apk [here](https://drive.google.com/file/d/1tMtDtJwuDZoQnxluiAPNC0dYs7aqXUjt/view?usp=sharing)

<!-- TOC --><a name="further-reading"></a>
### <a id="links" href="#links">Further Reading</a>

The links below provide a reinforced understanding to the library.
* [Form Builder Documentation](https://jkuatdsc.github.io/form-builder/).
* [Introduction to Form Builder basics](https://www.section.io/engineering-education/jetpack-compose-forms/)
* [Advanced Form Builder operations guide](https://www.section.io/engineering-education/making-jetpack-form-builder/)

<!-- TOC --><a name="contribution-guideline"></a>
### <a id="contribution" href="#contribution">Contribution Guideline</a>

We appreciate your interest in contributing to our project! To ensure a smooth and collaborative process, please follow these guidelines.

#### 1. Fork the Repository
- Begin by **forking** the repository to your GitHub account.
- **Clone** the forked repository to your local machine.

#### 2. Create a Feature Branch
- Create a new branch for your feature or bug fix. Branch names should follow this format:
  ```bash
  feature/<feature-name>
  fix/<bug-description>
  ```
#### 3. Make Changes and commit
- Make your changes on the feature branch.
- Keep your commits small, focused, and meaningful.

#### 4. Rebase with Develop Branch
- Before opening a pull request, rebase your feature branch with the latest `develop` branch
  ```bash
  git checkout develop
  git pull origin develop
  git checkout <your-branch>
  git rebase develop
  ```
#### 5. Create a Pull Request (PR)
- Once your feature branch is up to date with `develop`, create a pull request (PR) to merge into the `develop` branch.
- Ensure that the PR description includes:
  - A summary of the changes.
  - Any relevant issue references (e.g., "Closes #issue-number")
  - Details about any potential impact on existing functionality.
    
#### 6. Code Review
- Wait for a code review from maintainers or team members. Address any feedback by making further changes and pushing them to the same feature branch.

#### 7. Merge into Develop
- After approval and successful tests, your PR will be merged into the `develop` branch.
  
#### 8. Merging to Main
- Once the changes in `develop` are stable, they will be merged into the `main` branch as part of a release cycle.
  
#### 9. Updating Documentation
- If your changes require updates to the documentation, ensure you update the relevant files or create new ones before submitting your PR.
  
#### 10. Best Practices
- Write tests for any new features or bug fixes.
- Follow the existing coding standards and style guides used in the project.
- Ensure that your changes pass any automated tests and that the project builds successfully.
  
#### 11. Contribution Etiquette
- Do not maliciously delete any library functionality, change the name or package of the library, or amend any library information unless there is a well-justified reason for doing so.
- Be respectful and collaborative when communicating with other contributors or maintainers.
- When submitting a PR, please ensure that your comments and descriptions are clear and concise.
- Avoid large, unrelated changes in a single commit. Keep your work focused on the task at hand

Your collaboration is key to the success of this project, and every contribution matters.
Thank you for being part of our journey!✨

MIT [Licence](LICENSE)
