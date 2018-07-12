package com.elsen.jetpack.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.elsen.jetpack.base.data.local.LocalDbModule
import com.elsen.jetpack.base.data.server.RetrofitModule
import com.elsen.jetpack.comments.base.CommentModule
import com.elsen.jetpack.userdetail.base.UserModule
import com.mooveit.library.Fakeit
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.applicationContext

/**
 * Created by elsennovraditya on 23/06/18
 */
class JetpackApplication : Application(), Application.ActivityLifecycleCallbacks, AnkoLogger {

    override fun onActivityPaused(activity: Activity?) {
        info { "onActivityPaused: ${activity?.localClassName}" }
    }

    override fun onActivityResumed(activity: Activity?) {
        info { "onActivityResumed: ${activity?.localClassName}" }
    }

    override fun onActivityStarted(activity: Activity?) {
        info { "onActivityStarted: ${activity?.localClassName}" }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        info { "onActivityDestroyed: ${activity?.localClassName}" }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        info { "onActivitySaveInstanceState: ${activity?.localClassName}" }
    }

    override fun onActivityStopped(activity: Activity?) {
        info { "onActivityStopped: ${activity?.localClassName}" }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        info { "onActivityCreated: ${activity?.localClassName}" }
    }

    private val baseModule = applicationContext {
        bean("application_context") { this@JetpackApplication.applicationContext }
    }

    override fun onCreate() {
        super.onCreate()
        initFakeIt()
        initKoin()
        initActivityCallbacks()
    }

    private fun initFakeIt() {
        Fakeit.init()
    }

    private fun initKoin() {
        startKoin(
            this,
            listOf(
                baseModule,
                UserModule.module,
                CommentModule.module,
                RetrofitModule.module,
                LocalDbModule.module
            )
        )
    }

    private fun initActivityCallbacks() {
        registerActivityLifecycleCallbacks(this)
    }

}