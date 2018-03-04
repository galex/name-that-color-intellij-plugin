# Name That Color - Android Studio Plugin

## Introduction

When I get a new color to set in my Android app, I never know how to call it. 

I used the website [Name That Color](http://chir.ag/projects/ntc/) for years, and I wanted to have it integrated inside Android Studio, so I ported its JS library to Kotlin and built this IntelliJ plugin around it.

Besides its long [list of colors](https://github.com/galex/name-that-color-intellij-plugin/blob/master/namethatcolor/src/main/kotlin/il/co/galex/namethatcolor/core/util/Colors.kt), users requested to add the material colors names, so we can now name our colors as [material colors](https://github.com/galex/name-that-color-intellij-plugin/blob/master/namethatcolor/src/main/kotlin/il/co/galex/namethatcolor/core/util/MaterialColors.kt) as well. 

This plugin lets you insert a name for a color you copy-paste or type in an android resource file.

# Usage

Two options are available: Clipboard and Quick Fix.

### Clipboard

When you copy your color to add it in your app, you can use **CONTROL+SPACE** instead of pasting it to insert directly the right `<color>` tag. 

![after entering CONTROL+SPACE](/screenshots/clipboard.png)

### Quick Fix

You can insert "aaa" and an error warning in the form of a Quick Fix will show up so you can replace "aaa" with its own `<color name="silver_chalice">#aaa</color> ` tag by using **ALT+ENTER**.

![after entering ALT+ENTER](/screenshots/quick-fix.png)

# Color Input 

The Plugin accepts all formats of colors allowed in Android (lowercase or uppercase), and you don't need to prefix your color with "#":
* Color
  * **abc** -> **#abc**
  * **#abc** -> **#abc**
  * **aBc** -> **#aBc**
  * **ab12dc** -> **#ab12dc**
  * **#ab12dc** -> **#ab12dc** 
* Alpha + Color 
  * **6abc** -> **#6abc**
  * **#6abc** -> **#6abc**
  * **60ab12dc** -> **#60ab12dc**
  * **#60ab12dc** -> **#60ab12dc**
    
# Names

### Name that color

If you chose to use "Name That Color", this [list of colors](https://github.com/galex/name-that-color-intellij-plugin/blob/master/namethatcolor/src/main/kotlin/il/co/galex/namethatcolor/core/util/Colors.kt) is used. If the exact color is not found, the algorithm will find the closest match, if possible. 

##### Examples

* Color
  * **abc** -> `<color name="casper">#abc</color>`
  * **ab12dc** -> `<color name="electric_violet">#ab12dc</color>`
* Alpha + Color 
  * **6abc** -> `<color name="casper_alpha_40">#6abc</color>`
  * **60ab12dc** -> `<color name="electric_violet_alpha_38">#60ab12dc</color>`

### Name that material color

If you chose to use "Name That Color", this [list of material colors](https://github.com/galex/name-that-color-intellij-plugin/blob/master/namethatcolor/src/main/kotlin/il/co/galex/namethatcolor/core/util/MaterialColors.kt) is used. If the exact color is not found, the algorithm will find the closest match, if possible.

##### Examples

* Color
  * **abc** -> `<color name="blue_grey_200">#abc</color>`
  * **ab12dc** -> `<color name="purple_a700">#ab12dc</color>`
* Alpha + Color 
  * **6abc** -> `<color name="blue_grey_200_alpha_40">#6abc</color>`
  * **60ab12dc** -> `<color name="purple_a700_alpha_38">#60ab12dc</color>`

## Install

In Android Studio, open **Settings** > **Plugins** > **Browse Plugins and type** "name that color".

## Thanks

I'd like to thank [aednlaxer](https://github.com/aednlaxer) for submitting bugs and ideas!

## License 

This plugin is released under the: Creative Commons License:
Attribution 2.5 http://creativecommons.org/licenses/by/2.5/