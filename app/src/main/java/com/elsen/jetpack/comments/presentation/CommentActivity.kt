package com.elsen.jetpack.comments.presentation

import com.elsen.jetpack.R
import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info

/**
 * Created by elsennovraditya on 03/07/18
 */
class CommentActivity : NaviAppCompatActivity(), AnkoLogger {

    init {
        initLayout()
    }

    private fun initLayout() {
        RxNavi
            .observe(this, Event.CREATE)
            .subscribe(
                {
                    info { "onNext" }
                    setContentView(R.layout.activity_comment)
                },
                {
                    error("onError", it)
                }
            )
    }

}