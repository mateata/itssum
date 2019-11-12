package con.jwlee.itssum.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import con.jwlee.itssum.ui.BaseActivity
import android.widget.ViewFlipper
import con.jwlee.itssum.R
import kotlinx.android.synthetic.main.splash_view.*
import android.view.animation.AnimationUtils
import con.jwlee.itssum.MainActivity


class SplashActivity : BaseActivity(), View.OnTouchListener {

    lateinit var flipper: ViewFlipper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_view)
        //ViewFlipper 객체 참조
        flipper= flipper_view


        //ViewFlipper가 View를 교체할 때 애니메이션이 적용되도록 설정
        //애니메이션은 안드로이드 시스템이 보유하고 있는  animation 리소스 파일 사용.
        //ViewFlipper의 View가 교체될 때 새로 보여지는 View의 등장 애니메이션
        //AnimationUtils 클래스 : 트윈(Tween) Animation 리소스 파일을 Animation 객체로 만들어 주는 클래스
        //AnimationUtils.loadAnimaion() - 트윈(Tween) Animation 리소스 파일을 Animation 객체로 만들어 주는 메소드
        //첫번째 파라미터 : Context
        //두번재 파라미터 : 트윈(Tween) Animation 리소스 파일(여기서는 안드로이드 시스템의 리소스 파일을 사용
        //(왼쪽에서 슬라이딩되며 등장)


        flipper.setOnTouchListener(this)
        skipbtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

    }

    var idx = 0
    var m_preTouchPosX = 0
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN) {
            m_preTouchPosX = event.getX().toInt()
        }

        if(event.action == MotionEvent.ACTION_UP) {
            var nTouchPosX = event.getX().toInt()

            if (nTouchPosX < m_preTouchPosX) {
                moveNextView()
            } else if (nTouchPosX > m_preTouchPosX) {
                movePreview()
            }
            m_preTouchPosX = nTouchPosX
        }

        return true
    }


    fun moveNextView() {
        if(idx == 2) {
            return
        }
        flipper.inAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_right)
        flipper.outAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_left)
        flipper.showNext()
        idx++
        if(idx == 2) {
            skipbtn.setBackgroundResource(R.drawable.startbtn)
        }
    }

    fun movePreview() {
        if(idx == 0) {
            return
        }
        flipper.inAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_in_left)
        flipper.outAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_slide_out_right)
        flipper.showPrevious()
        idx--
        if(idx < 2) {
            skipbtn.setBackgroundResource(R.drawable.skipbtn)
        }
    }
}