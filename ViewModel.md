# ViewModel
Android Jetpack 是一系列助力您更容易打造优秀 Android 应用的工具和组件。这些组件能帮助您遵循最佳实践、免除编写繁复的样板代码并简化复杂任务，从而使您可以专注于最核心的代码逻辑。 Jetpack 中的架构指南由 Android 开发中四个关键领域中的一系列代码库和工具提供支持。它们分别是基础、架构、行为和 UI。每个 Jetpack 组件均可单独采用，但它们依然可以流畅地协作。其中的 Lifecycle 库,可以有效避免内存泄漏和解决常见的 Android 生命周期难题,今天将介绍 Lifecycle 库中的 ViewModel 类 。

![Architecture Component](https://s2.ax1x.com/2019/01/29/kQvpu9.png)

## 介绍

简单来说,ViewModel 是用来保存应用 UI 数据的类,而且它会在配置变更后继续存在。它可以脱离 View 单纯做 Junit 的测试，更方便大家做单元测试。

## 解决了什么问题

手机屏幕旋转是配置变更的一种，当旋转屏幕时， Activity 会被重新创建。如果数据没有被正确的保存和恢复，就有可能丢失。从而导致莫名其妙的 UI 错误，甚至应用崩溃。

![应用 UI 数据丢失](https://s2.ax1x.com/2019/01/29/kQOejs.png)

相反的， ViewModel 会在配置更改后继续存在，所以，如果将应用所有的 UI 数据保存在 ViewModel 中，而不是 Activity 中，这样就可以保证数据不会受到配置变更的影响了。

![数据保存在 ViewModel 中](https://s2.ax1x.com/2019/01/29/kQOs8e.png)

![ViewModel生命周期](https://developer.android.com/images/topic/libraries/architecture/viewmodel-lifecycle.png)

新手常见犯的错误是将很多业务变量，逻辑和数据都摆在 Activity 或 Fragment 中，这样的代码比较混乱和难以维护，这种开发模式违犯了单一责任的原则。而 ViewModel 可以有效地划分责任，它可以用来保存 Activity 的所有 UI 数据,然后 Activity 仅负责了解如何在屏幕上显示数据和接收用户互动，但是 Activity 不会处理这些互动所带来的业务逻辑（如：点击获取短信验证码按钮后的网络请求）。但不要将太多的逻辑处理，数据获取放到 ViewModel 类里面处理，它仅仅作为 UI 数据的保存！

## 简单使用

上面提到，不要将太多的逻辑处理、数据存储获取放到 ViewModel 类里面处理，它仅仅作为 UI 数据的保存！数据存储获取可以创建 Repository 类， UI 数据处理可以使用 Presenter 类。

![各类职责](https://s2.ax1x.com/2019/01/29/kQX0Ln.png)

ViewModel 的使用方式如下：

```gradle
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
```

```kotlin
//继承系统 ViewModel 类
class UserProfileViewModel:ViewModel(){
    //将跟 UI 界面有关的数据放至 UserProfileViewModel 中
    val user = User(name="",company="")
}
```

```kotlin
//在 Activity 中实例化 UserProfileViewModel 变量 
override fun onCreate(saveInstanceState:Bundle?){
    //Setup Activity
    val userViewModel =  ViewModelProviders.of(this).get(UserProfileViewModel::class.java)
    userViewModel.user.name = "Edgar Ng"
}
```

## 实际应用

## 内部实现

## 源码地址

## 参考
1. [Android Jetpack - ViewModel | 中文教学视频](https://www.bilibili.com/video/av29949898)