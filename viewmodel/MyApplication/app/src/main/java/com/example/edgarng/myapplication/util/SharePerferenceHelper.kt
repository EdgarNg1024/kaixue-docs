package com.example.edgarng.myapplication.util

import android.content.Context
import android.content.SharedPreferences

/**
 * ---------------------
 * 作者：峥嵘life
 * 来源：CSDN
 * 原文：https://blog.csdn.net/wenzhi20102321/article/details/53431407
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 *
 *
 * 这是一个SharePreference的工具类，使用它可以更方便的数据进行简单存储和提取
 * 这里只要知道基本调用方法就可以了
 * 1.通过构造方法来传入上下文和文件名
 * 2.通过putValue方法传入一个或多个自定义的ContentValue对象，进行数据存储
 * 3.通过getXXX方法来获取数据
 * 4.通过clear方法来清除这个文件的数据
 * 这里没有提供清除单个key的数据，是因为存入相同的数据会自动覆盖，没有必要去理会，当然也是可以添加的
 */

class SharePreferencesHelper
//定义一个上下文对象

//创建SharePreference对象时要上下文和存储的模式
//通过构造方法传入一个上下文
    (context: Context, fileName: String) {
    //定义一个SharePreference对象
    internal var sharedPreferences: SharedPreferences

    init {
        //实例化SharePreference对象，使用的是get方法，而不是new创建
        //第一个参数是文件的名字
        //第二个参数是存储的模式，一般都是使用私有方式：Context.MODE_PRIVATE
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    /**
     * 存储数据
     * 这里要对存储的数据进行判断在存储
     * 只能存储简单的几种数据
     * 这里使用的是自定义的ContentValue类，来进行对多个数据的处理
     */
    //创建一个内部类使用，里面有key和value这两个值
    class ContentValue//通过构造方法来传入key和value
        (internal var key: String, internal var value: Any)

    //一次可以传入多个ContentValue对象的值
    fun putValues(vararg contentValues: ContentValue) {
        //获取SharePreference对象的编辑对象，才能进行数据的存储
        val editor = sharedPreferences.edit()
        //数据分类和存储
        for (contentValue in contentValues) {
            //如果是字符型类型
            if (contentValue.value is String) {
                editor.putString(contentValue.key, contentValue.value.toString()).commit()
            }
            //如果是int类型
            if (contentValue.value is Int) {
                editor.putInt(contentValue.key, Integer.parseInt(contentValue.value.toString())).commit()
            }
            //如果是Long类型
            if (contentValue.value is Long) {
                editor.putLong(contentValue.key, java.lang.Long.parseLong(contentValue.value.toString())).commit()
            }
            //如果是布尔类型
            if (contentValue.value is Boolean) {
                editor.putBoolean(contentValue.key, java.lang.Boolean.parseBoolean(contentValue.value.toString()))
                    .commit()
            }

        }
    }


    //获取数据的方法

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, -1)
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, -1)
    }

    //清除当前文件的所有的数据
    fun clear() {
        sharedPreferences.edit().clear().commit()
    }

}
