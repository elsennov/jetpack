package com.elsen.jetpack.userdetail.presentation

import android.databinding.DataBindingUtil
import com.elsen.jetpack.R
import com.elsen.jetpack.base.toObservable
import com.elsen.jetpack.databinding.ActivityUserDetailBinding
import com.jakewharton.rxbinding2.view.RxView
import com.mooveit.library.Fakeit
import com.trello.navi2.Event
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.navi2.rx.RxNavi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.koin.android.architecture.ext.viewModel

/**
 * Created by elsennovraditya on 21/06/18
 */
class UserDetailActivity : NaviAppCompatActivity(), AnkoLogger {

    companion object {
        const val USER = "user"
    }

    private var binding: ActivityUserDetailBinding? = null

    init {
        initLayout()
        initUserDetailInfo()
        initChangeUserButton()
    }

    private val userVm: UserViewModel by viewModel()

    private fun initLayout() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                {
                    binding = DataBindingUtil.setContentView(
                        this, R.layout.activity_user_detail
                    )
                },
                { error("onError", it) }
            )
    }

    private fun initUserDetailInfo() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(Schedulers.io())
            .flatMap { userVm.getUser(intent).toObservable(this) }
            .observeOn(AndroidSchedulers.mainThread())
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                {
                    info { "onNext" }
                    setUserDetail(it)
                },
                {
                    error("onError", it)
                    setUserDetail(UserDisplayable("error", "error", "error"))
                },
                {
                    info { "onComplete" }
                }
            )
    }

    private fun setUserDetail(userDisplayable: UserDisplayable?) {
        binding?.user = userDisplayable
    }

    private fun initChangeUserButton() {
        RxNavi
            .observe(this, Event.CREATE)
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { RxView.clicks(change_user_button) }
            .takeUntil(RxNavi.observe(this, Event.DESTROY))
            .subscribe(
                {
                    userVm.setUser(
                        UserDisplayable(
                            Fakeit.name().firstName(),
                            Fakeit.company().name(),
                            Fakeit.job().title()
                        )
                    )
                },
                {
                    error("onError", it)
                    userVm.setUser(UserDisplayable("error", "error", "error"))
                }
            )
    }

}