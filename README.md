## Jetpack Compose FormBuilder
A highly android library used to provide an abstraction layer over form elements as well as provide a DRY code implementation of a form.

- [Introduction](#introduction) 
- [Features](#features)
- [Usage](#usage)

### Introduction
The library aims to provide a dynamic way to build a `Form` using Jetpack compose input fields. There are common actions that are performed with forms such as validation and management of data. 

With the current version of Jetpack compose, we don't have a `Form` composable which leads to a lot of redundant code that can get messy really quick. Assume you have 10 text fields, you have to manage the state and validation of each field individually. This will lead to a lot of variables and if/when expressions in that single composable. 

### Features
### Usage

MIT [Licence](LICENSE)