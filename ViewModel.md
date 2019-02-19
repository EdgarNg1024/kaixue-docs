# ViewModel
Android Jetpack 是一系列助力您更容易打造优秀 Android 应用的工具和组件。这些组件能帮助您遵循最佳实践、免除编写繁复的样板代码并简化复杂任务，从而使您可以专注于最核心的代码逻辑。 Jetpack 中的架构指南由 Android 开发中四个关键领域中的一系列代码库和工具提供支持。它们分别是基础、架构、行为和 UI。每个 Jetpack 组件均可单独采用，但它们依然可以流畅地协作。其中的 Lifecycle 库,可以有效避免内存泄漏和解决常见的 Android 生命周期难题,今天将介绍 Lifecycle 库中的 ViewModel 类 。

![Architecture Component](https://s2.ax1x.com/2019/01/29/kQvpu9.png)

## 介绍

简单来说,ViewModel 是用来保存应用 UI 数据的类,而且它会在配置变更后继续存在。它可以脱离 View 单纯做 Junit 的测试，更方便大家做单元测试。ViewModel 有这样的特性，但是它在实际项目能够有何表现，还需要看待各位是如何使用的。一般会和 Livedata、databinding 等其他组件进行组合使用，下面会介绍具体可以解决什么问题，和一般项目中会建议如何使用。在介绍的过程中，还会穿插一些架构的思想以及如何看源码分析源码的思路。

## 解决了什么问题

手机屏幕旋转是配置变更的一种，当旋转屏幕时， Activity 会被重新创建。如果数据没有被正确的保存和恢复，就有可能丢失。从而导致莫名其妙的 UI 错误，甚至应用崩溃。Demo请点击[这里](https://github.com/EdgarNg1024/kaixue-docs/tree/master/viewmodel/MyApplication)

![kyHxB9.gif](https://s2.ax1x.com/2019/02/17/kyHxB9.gif)

![应用 UI 数据丢失](https://s2.ax1x.com/2019/01/29/kQOejs.png)

相反的， ViewModel 会在配置更改后继续存在，所以，如果将应用所有的 UI 数据保存在 ViewModel 中，而不是 Activity 中，这样就可以保证数据不会受到配置变更的影响了。

![kybeHA.gif](https://s2.ax1x.com/2019/02/17/kybeHA.gif)

![数据保存在 ViewModel 中](https://s2.ax1x.com/2019/01/29/kQOs8e.png)

![ViewModel 生命周期](https://s2.ax1x.com/2019/01/31/k3kAoD.png)

新手常见犯的错误是将很多业务变量，逻辑和数据都摆在 Activity 或 Fragment 中，这样的代码比较混乱和难以维护，这种开发模式违犯了单一责任的原则。而 ViewModel 可以有效地划分责任，它可以用来保存 Activity 的所有 UI 数据,然后 Activity 仅负责了解如何在屏幕上显示数据和接收用户互动，但是 Activity 不会处理这些互动所带来的业务逻辑（如：点击获取短信验证码按钮后的网络请求）。但不要将太多的逻辑处理，数据获取放到 ViewModel 类里面处理，它仅仅作为 UI 数据的保存！

## 简单使用

上面提到，不要将太多的逻辑处理、数据存储获取放到 ViewModel 类里面处理，它仅仅作为 UI 数据的保存！数据存储获取可以创建 Repository 类， UI 数据处理可以使用 Presenter 类。Demo请点击[这里](https://github.com/EdgarNg1024/kaixue-docs/tree/master/viewmodel/MyApplication)

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

> ViewModel 一般会搭载其他组件（如：LiveData 等）一同使用，本系列文章会专门出一篇文章就 ViewModel 及其他组件搭配使用进行说明讲解，请点击[此处]。
>本章也会简述实际项目中会如何使用 ViewModel ，本章代码使用了 Google I/O 2018 的 APP github 的源码进行讲解，如需查看全部源码，请点击[这里](https://github.com/google/iosched/ )，如需了解整个项目的架构，请点击 [这里](https://medium.com/androiddevelopers/google-i-o-2018-app-architecture-and-testing-f546e37fc7eb )。本章主要阐述 ViewModel 是如何跟其他组件合作搭配使用。

[![kDGB3q.png](https://s2.ax1x.com/2019/02/15/kDGB3q.png)](https://imgchr.com/i/kDGB3q)

该 Google I/O app 总得来说分为三层：展示层（Presentation Layer）、域层（Domain Layer）、数据层（Data Layer），如下图所示。

+ 展示层：View，ViewModel
+ 域层：use cases
+ 数据层：repositories，user manager

展示层无法**直接**与数据层沟通，ViewModel 只可以通过一个或者多个 UseCase 去获取 repository，这种分层的做法有助于降低耦合度和提高可测试性，同时方便管理，让所有的 UseCase 都在耗时线程中执行，保证在 UI 线程中没有耗时数据的入口。

![Google I/O 2018 app数据架构](https://s2.ax1x.com/2019/02/12/kdEJ4s.png)

展示层具体使用以下技术：Views + ViewModels + Data Binding + LiveData

ViewModels 从 LiveData 那里获取数据提供给 Views，通过 Data Binding 真正的 UI 控件就可以自动完成数据显示，可以从 Activity 和 Fragment 那些繁杂而单调的样式代码中解放出来。

### 业务事例

接下来我们会通过讲述 Google I/O 中日程活动详情业务来说明 ViewModel 是如何通过和其他组件合作来完成任务的。

[![kyLeYt.md.jpg](https://s2.ax1x.com/2019/02/17/kyLeYt.md.jpg)](https://imgchr.com/i/kyLeYt)



### ViewModel 的 Unit test

## 内部实现

>偏向源码以及设计思想分析，不感兴趣的同学可以跳过这章，谢谢！
>源码来源编文时最新 lifecycle-viewmodel-2.1.0-alpha02-sources.jar,截图已经附上行号,方便比对

为了更好地详细了解 ViewModel,我们可以带着几个问题去学习:
1. ViewModel 是怎么生成的?
1. ViewModel 为什么可以达到这么神奇的效果，Activity/Fragment 都重新生成居然还可以活下来,它是怎么做到的?

### 1. 常见的 ViewModel 实现方式:

```kotlin
val ViewModel1 = ViewModelProviders.of(activity,factory).get(ViewModel::class.java)
val ViewModel2 = ViewModelProviders.of(fragment,factory).get(ViewModel::class.java)
```

ViewModel 主要实现机制采用了工厂模式，传入 Factory 去实例化指定的ViewModel类，如果不传 Factory，则使用默认的，如下图。
![默认的 Factory](https://s2.ax1x.com/2019/02/02/k85Dvn.png)

具体的调用流程如下，由此可以看出ViewModel的生成其实还是由我们传入的 `factory.create()` 方法来生成

![ViewModel 生成过程](https://s2.ax1x.com/2019/02/02/k8TxxJ.png)

Ps：请注意 ViewModelProvider 和 ViewModelProviders，是两个独立的类。具体可以从下列截图看出，ViewModelProviders 是 LifeCycle 库的扩展类，高内聚低耦合，核心的东西放在一个类，有可能变化的扩展的东西放在另外一个地方进行扩展，这也可以启发到我们新手在设计框架时要考虑到扩展性，尤其是做 SDK 或者底层架构的同学可以参考学习这一块的设计方式。

![ViewModelProvider 和 ViewModelProviders 的区别.png](https://s2.ax1x.com/2019/02/02/k8IIJg.png)

### 2.为什么 ViewModel 的生命周期这么长(比一般的 UI 数据长)

![ViewModel 生命周期](https://s2.ax1x.com/2019/01/31/k3kAoD.png)

首先我们重温一下 ViewModel 的生命周期(如上图)，ViewModel 的生命周期不会因为配置变更（Activity 的多次调用`onCreate()`方法）会终止，反而会和 Activity 的 `onDestroy()`方法一起结束。为什么可以这样子？！请接着往下看。

![kcd0C6.png](https://s2.ax1x.com/2019/02/18/kcd0C6.png)

要想知道 ViewModel 的生命周期，思路上当然是从 ViewModel 那里获得答案（其实从 UI 控制器那里获取更快，不过这算作弊）。根据 ViewModel 生成的流程图可以知道，是调用了 `ViewModelProvider.get(@NonNull String key, @NonNull Class<T> modelClass)` 返回生成 ViewModel 的，查看源码 get 方法（上图）可以知道，一共有两处地方生成 ViewModel 的，`ViewModel viewModel = mViewModelStore.get(key);` 和 `viewModel = mFactory.create(key, modelClass);` ，但是生成完了之后都通过 `mViewModelStore.put(key, viewModel);` ，通过方法名称应该就是一个缓存器，查看 `mViewModelStore.put(key, viewModel)`的源码后，发现就是常见的内部缓存了一个Map进行对ViewModel的管理。

![ViewModelStore](https://s2.ax1x.com/2019/02/03/k8va5R.md.png)

那么这个 `mViewModelStore` **是什么**？**为什么要缓存 `viewModel` 呢**？

这个 `mViewModelStore` 就是实例化 ViewModelProvider 时候传进来的（请见 ViewModel 生成的流程图，由 `activity.getViewModelStore()` 或 `fragment.getViewModelStore()` 获得）。

![kcwoJx.png](https://s2.ax1x.com/2019/02/18/kcwoJx.png)

了解 `mViewModelStore` 是什么之后，我们就解决第二个疑问？ UI 控制器中的属性 `mViewModelStore` 为什么要缓存viewModel？难道是想重新拿出来用，来达到 `ViewModel` 的长生命周期？(因为刚刚上上图提到第一处生成 `ViewModel` 的就是从 `mViewModelStore` 那里 `get(key)` 出来的)接下来我们就去了解 `mViewModelStore` 在 Activity 和 Fragment 中的作用了，我们先了解 Activity 的。

在 Activity 中能够看到 `getViewModelStore()` 方法，mViewModelStore 有三种方式，1.自身不为 null ；2. `nc.viewModelStore` ;3. `new ViewModelStore();`。通过查看 `mViewModelStore` 在整个 `Activity` 类中，只有这个方法用到，所以 `mViewModelStore` 只能从2 3中获得实例。3不用说，直接看2 --> `getLastNonConfigurationInstance()` 从名字上看相当有嫌疑！

![kc0zB4.png](https://s2.ax1x.com/2019/02/18/kc0zB4.png)

点击 `getLastNonConfigurationInstance()` 查看源码，发现描述如下图。通过描述可以知道，我们没有找错！！获得之前由{@link #onRetainNonConfigurationInstance（）}返回的 non-configuration instance data 。这将从初始化 {@link #onCreate} 和 {@link #onStart} 调用新实例中获得 non-configuration instance data ，允许您从**前一个实例**中提取任何有用的动态状态。话虽如此，但是还是想知道 `mLastNonConfigurationInstances` **它是怎么来的？为什么可以把上一个 Activity 的 NonConfigurationInstances 数据拿到现在的 Activity 来**

![kcDusU.png](https://s2.ax1x.com/2019/02/18/kcDusU.png)

根据描述，是从 `onRetainNonConfigurationInstance（）` 方法那里来的，但是查看了 `Activity` 的这个方法，发现方法里面是 `null` ！然后难道是子类做了实现？为什么会这么想？

![kgIaA1.png](https://s2.ax1x.com/2019/02/19/kgIaA1.png)

![kc0zB4.png](https://s2.ax1x.com/2019/02/18/kc0zB4.png)

![kg7N7j.png](https://s2.ax1x.com/2019/02/19/kg7N7j.png)

因为 `public static ViewModelProvider of(@NonNull FragmentActivity activity,@Nullable Factory factory)` 中使用的是 `FragmentActivity` 而不是 `Activity` ，在方法设计上会偏向使用基类来进行参数传递。而且之前的 `mViewModelStore` 也是由 `FragmentActivity` 的父类 `ComponentActivity` 生成的，而且 `NonConfigurationInstances` 类也是 `ComponentActivity` 的内部类。所以我们这才推断是不是 `Activity` 的子类实现了。同时我们也可以学习到：

> 1. 要对 `Activity`、`ComponentActivity`、`FragmentActivity`、`AppCompatActivity` 之间的异同有所涉猎，尤其在设计底层框架以及接口的时候，要慎重选择，不然会设计出来不好用，处处受阻。
> 1. 设计底层的时候，类适当的分层继承有助于架构的清晰解耦。

我们只需查看 `FragmentActivity` 和 `ComponentActivity` 哪个类实现了 `onRetainNonConfigurationInstance（）` 就可以了。

查看 `FragmentActivity` 的父类 `ComponentActivity` 的时候可以见到






所以这也同时回答了“ **ViewModel 是不是一定不能绑定 Activity、Fragment 的问题？**”

而 ViewModel 什么时候才会被 clear 掉呢？根据 ViewModel 的生命周期图标，应该是 activity 调用 `onFinish()` 会调用 ViewModel 的 `clear()`。我们进一步查看哪里调用了 ViewModel 的 `clear()` 方法，可知会从 `ComponentActivity.Java` 和 `FragmentManagerViewModel.java` 处有所调用，我们先分析 `ComponentActivity.Java`。

由下图可以看到，当 ComponentActivity 是 `ON_DESTROY` 状态时，而且不是``配置变更``时，会调用 `ViewModelStore.clear()` 方法清空与之绑定的 ViewModel 数据。

![ComponentActivity清空数据](https://s2.ax1x.com/2019/02/03/k8xclV.png)

由下图 FragmentManagerViewModel 清空数据分析可见，当 Fragment 应该 Destroy 时,而且不是``配置变更``时，会调用 `ViewModelStore.clear()` 方法清空与之绑定的 ViewModel 数据。

![k8zBnO.png](https://s2.ax1x.com/2019/02/03/k8zBnO.png)

![k8z29I.png](https://s2.ax1x.com/2019/02/03/k8z29I.png)

所以这就解析了，ViewModel 的生命周期为什么可以跟 Activity 一样长，以及为什么可以在配置变更后还可以继续复用了。

## 总结
1. ViewModel 本身只是一个保存数据的类，需要搭配 livedata、databinding 才能发挥效果。单独用作用不大，不过好处是解耦，方便 unit test，效果很明显！这篇只是推广初级文章，很多注意的细节点，可以点击[这里](https://github.com/xitu/gold-miner/blob/master/TODO/viewmodels-and-livedata-patterns-antipatterns.md)
1. ViewModel 尽量不要绑定跟Android包有关的类，有可能造成内存泄漏（具体请见[内部实现]章节）以及会给 Unit Test 造成困难，Google 建立 JetPack 的其中一个目的就是解耦可测试！

## 源码地址
[https://github.com/google/iosched/](https://github.com/google/iosched/)

## 参考
1. [Android Jetpack - ViewModel | 中文教学视频](https://www.bilibili.com/video/av29949898)
1. [ViewModels and LiveData: Patterns + AntiPatterns](https://medium.com/google-developers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54) [(译文)](https://github.com/xitu/gold-miner/blob/master/TODO/viewmodels-and-livedata-patterns-antipatterns.md)[强烈推荐，作者应该是 Google 里面有参与 ViewModel 的实现开发的，所以文章的推荐用法应该是最贴近 ViewModel 开发思想]
1. [ViewModels: Persistence, onSaveInstanceState(), Restoring UI State and Loaders](https://medium.com/google-developers/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders-fc7cc4a6c090) [(译文)](https://github.com/xitu/gold-miner/blob/master/TODO/viewmodels-persistence-onsaveinstancestate-restoring-ui-state-and-loaders.md)[强烈推荐，作者应该是 Google 里面有参与 ViewModel 的实现开发的，所以文章的推荐用法应该是最贴近 ViewModel 开发思想]
1. [Google I/O 2018 app — Architecture and Testing](https://medium.com/androiddevelopers/google-i-o-2018-app-architecture-and-testing-f546e37fc7eb)[强烈推荐，作者应该是 Google 里面有参与 ViewModel 的实现开发的，所以文章的推荐用法应该是最贴近 ViewModel 开发思想]

## 撰稿人
Edgar Ng（https://github.com/EdgarNg1024)