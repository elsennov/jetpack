package com.elsen.jetpack.comments.presentation

import android.support.v7.widget.LinearLayoutManager
import com.elsen.jetpack.R
import com.elsen.jetpack.base.presentation.getAdapter
import com.elsen.jetpack.base.presentation.makeGone
import com.elsen.jetpack.base.presentation.makeVisible
import com.jakewharton.rxbinding2.view.RxView
import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_comment.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.koin.android.architecture.ext.viewModel

/**
 * Created by elsennovraditya on 03/07/18
 */
class CommentActivity : NaviAppCompatActivity(), AnkoLogger {

    private val commentViewModel: CommentViewModel by viewModel()

    init {
        initLayout()
        initComments()
        initAddButton()
        initInitialLoadErrorObservation()
        initLoadMoreErrorObservation()
        initRetryLoadMoreButton()
    }

    private fun initLayout() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                {
                    info { "onNext" }
                    setContentView(R.layout.activity_comment)
                    initCommentsContainer()
                },
                {
                    error("onError", it)
                }
            )
    }

    private fun initCommentsContainer() {
        val layoutManager = LinearLayoutManager(this)
        comments_container.layoutManager = layoutManager
        comments_container.adapter = CommentAdapter()
    }

    private fun initComments() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(Schedulers.io())
            .flatMap { commentViewModel.getComments() }
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                {
                    info { "onNext: Submitted ${it.size}" }
                    info { "onNext: First ID ${if (it.size > 0) it[0]?.id else "-"}" }
                    (comments_container.adapter as CommentAdapter).submitList(it)
                },
                {
                    error("onError", it)
                }
            )
    }

    private fun initAddButton() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { RxView.clicks(add_comment_button) }
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe {
                info { "Total Item: ${comments_container.getAdapter<CommentAdapter>().itemCount}" }
            }
    }

    private fun initInitialLoadErrorObservation() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(Schedulers.io())
            .flatMap { commentViewModel.observeInitialLoadErrorEvent() }
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe { showRetryLoadMoreButton() }
    }

    private fun initLoadMoreErrorObservation() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(Schedulers.io())
            .flatMap { commentViewModel.observeLoadMoreErrorEvent() }
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe { showRetryLoadMoreButton() }
    }

    private fun showRetryLoadMoreButton() {
        retry_comment_button.makeVisible()
    }

    private fun initRetryLoadMoreButton() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { RxView.clicks(retry_comment_button) }
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe {
                hideRetryLoadMoreButton()
                retryLoadMore()
            }
    }

    private fun hideRetryLoadMoreButton() {
        retry_comment_button.makeGone()
    }

    private fun retryLoadMore() {
        commentViewModel.retryLoadMoreComments(
            comments_container.getAdapter<CommentAdapter>().getLastItem()
        )
    }

}