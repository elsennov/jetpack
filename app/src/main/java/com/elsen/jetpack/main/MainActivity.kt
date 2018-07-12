package com.elsen.jetpack.main

import com.elsen.jetpack.R
import com.elsen.jetpack.comments.presentation.CommentActivity
import com.elsen.jetpack.userdetail.presentation.UserDetailActivity
import com.elsen.jetpack.userdetail.presentation.UserDisplayable
import com.jakewharton.rxbinding2.view.RxView
import com.mooveit.library.Fakeit
import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.intentFor

/**
 * Created by elsennovraditya on 21/06/18
 */
class MainActivity : NaviAppCompatActivity(), AnkoLogger {

    init {
        initLayout()
        initUserDetailButton()
        initCommentsButton()
    }

    private fun initLayout() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                { setContentView(R.layout.activity_main) },
                { error("onError", it) }
            )
    }

    private fun initUserDetailButton() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { RxView.clicks(user_detail_button) }
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                { launchUserDetailActivity() },
                { error("onError", it) }
            )
    }

    private fun launchUserDetailActivity() {
        val user = UserDisplayable(
            Fakeit.name().firstName(),
            Fakeit.company().name(),
            Fakeit.job().title()
        )
        startActivity(
            intentFor<UserDetailActivity>(
                UserDetailActivity.USER to user
            )
        )
    }

    private fun initCommentsButton() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { RxView.clicks(comments_button) }
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                { launchCommentsActivity() },
                { error("onError", it) }
            )
    }

    private fun launchCommentsActivity() {
        startActivity(intentFor<CommentActivity>())
    }

}