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


```text
MIT License

Copyright (c) 2021 Kojo Fosu Bempa Edue

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```