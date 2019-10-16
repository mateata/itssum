package con.jwlee.itssum

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent



class IntroAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_act)

        window.decorView.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            // 액티비티 이동시 페이드인/아웃 효과를 보여준다. 즉, 인트로
            //    화면에 부드럽게 사라진다.
            overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }, 2000)

    }
}