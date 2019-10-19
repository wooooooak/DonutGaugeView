# DonutGaugeView

Donut Gauge View with animation for Android

![donutView_gif](https://user-images.githubusercontent.com/18481078/67137392-51d95500-f26f-11e9-8c8b-edf31d61cdc7.gif)

## Install

### Gradle

1. Add the JitPack repository to your project level build.gradle file

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

2. Add the dependency to your app level build.gradle file

```
dependencies {
    implementation 'com.github.wooooooak:donutguageview:\$version'
}
```

The latest version is 1.0.0. Checkout [here](https://github.com/wooooooak/DonutGaugeView/releases)

## Usage

### Basic Usage

```xml
<wooooooak.com.library.DonutGaugeView
    android:id="@+id/donutGaugeView1"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:dg_start_value="70"
    app:dg_end_value="100"
    />
```

`dg_end_value` is required because of Circle ratio calculation.

### Use initValue() to redraw DonutGuageView

```kotlin
donutGuageView.run {
    topText = "new top"
    bottomText = "200"
    initValue(20f, 200f)
}
```

### Use updateValue() to update existing value

```kotlin
donutGuageView.updateValue(100f)
```

In this method, `End value` that you set before is kept. Only the current value will changed.

## Attribute name definition

![image](https://user-images.githubusercontent.com/18481078/67140578-cd9ac800-f296-11e9-851c-03d3ab7fa8e7.png)

## Contribution

All Pull Request and Issues are welcome!

## License

```
Copyright 2019 wooooooak (Yongjun LEE)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
