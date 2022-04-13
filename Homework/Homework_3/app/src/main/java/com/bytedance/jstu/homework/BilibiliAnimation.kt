package com.bytedance.jstu.homework

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView


class BilibiliAnimation : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {
    private var bilibiliCoinView: ImageView? = null
    private var bilibiliCoinBlueView: ImageView? = null
    private var bilibiliLikeView: ImageView? = null
    private var bilibiliLikeBlueView: ImageView? = null
    private var bilibiliRepostView: ImageView? = null
    private var bilibiliRepostBlueView: ImageView?= null

    private fun bindViewMember() {
        bilibiliCoinView = findViewById(R.id.bilibili_coin)
        bilibiliCoinBlueView = findViewById(R.id.bilibili_coin_blue)
        bilibiliLikeView = findViewById(R.id.bilibili_like)
        bilibiliLikeBlueView = findViewById(R.id.bilibili_like_blue)
        bilibiliRepostView = findViewById(R.id.bilibili_repost)
        bilibiliRepostBlueView = findViewById(R.id.bilibili_repost_blue)
    }

    private fun bindViewClickListener() {
        bilibiliCoinView?.setOnClickListener(this)
        bilibiliCoinBlueView?.setOnClickListener(this)
        bilibiliLikeView?.setOnClickListener(this)
        bilibiliLikeBlueView?.setOnClickListener(this)
        bilibiliRepostView?.setOnClickListener(this)
        bilibiliRepostBlueView?.setOnClickListener(this)
        bilibiliCoinView?.setOnLongClickListener(this)
        bilibiliCoinBlueView?.setOnLongClickListener(this)
        bilibiliLikeView?.setOnLongClickListener(this)
        bilibiliLikeBlueView?.setOnLongClickListener(this)
        bilibiliRepostView?.setOnLongClickListener(this)
        bilibiliRepostBlueView?.setOnLongClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bilibili_animation)
        bindViewMember()
        bindViewClickListener()
    }

    private fun viewClickAnimation(current_view: View?, paired_view: View?) {
        val positionAnimator = ValueAnimator.ofFloat(0f, -20f, 0f)
        positionAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            current_view?.translationY = value
        }
        val rotateAnimator = ValueAnimator.ofFloat(0f, -45f, 0f)
        rotateAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            current_view?.rotation = value
        }

        val animatorSet = AnimatorSet()
        animatorSet.play(positionAnimator).with(rotateAnimator)
        animatorSet.duration = 500L
        animatorSet.addListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                current_view?.visibility = View.INVISIBLE
                paired_view?.visibility = View.VISIBLE
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}
        })
        animatorSet.start()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            bilibiliCoinView -> {
                viewClickAnimation(bilibiliCoinView, bilibiliCoinBlueView)
            }
            bilibiliCoinBlueView -> {
                viewClickAnimation(bilibiliCoinBlueView, bilibiliCoinView)
            }
            bilibiliLikeView -> {
                viewClickAnimation(bilibiliLikeView, bilibiliLikeBlueView)
            }
            bilibiliLikeBlueView -> {
                viewClickAnimation(bilibiliLikeBlueView, bilibiliLikeView)
            }
            bilibiliRepostView -> {
                viewClickAnimation(bilibiliRepostView, bilibiliRepostBlueView)
            }
            bilibiliRepostBlueView -> {
                viewClickAnimation(bilibiliRepostBlueView, bilibiliRepostView)
            }
        }
    }

    override fun onLongClick(p0: View?): Boolean {
        bilibiliCoinView?.visibility = View.VISIBLE
        bilibiliLikeView?.visibility = View.VISIBLE
        bilibiliRepostView?.visibility = View.VISIBLE
        bilibiliCoinBlueView?.visibility = View.INVISIBLE
        bilibiliLikeBlueView?.visibility = View.INVISIBLE
        bilibiliRepostBlueView?.visibility = View.INVISIBLE
        viewClickAnimation(bilibiliCoinView, bilibiliCoinBlueView)
        viewClickAnimation(bilibiliLikeView, bilibiliLikeBlueView)
        viewClickAnimation(bilibiliRepostView, bilibiliRepostBlueView)
        return true
    }

}
