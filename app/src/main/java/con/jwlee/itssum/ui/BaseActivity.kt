package con.jwlee.itssum.ui

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import con.jwlee.itssum.R
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_tab.view.*


open class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .commit()
    }


    fun replaceFragment(fragment: Fragment, args : Bundle, tag: String) {
        fragment.arguments = args
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(null)
            .commit()
    }

    fun removeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .remove(fragment)
            .commit()
    }

    /* 자바에서 와일드카드로 쓰이는 <?>는 코틀린에선 <*>로 쓰인다 */
    fun startNextActivity(className: Class<*>) {
        var intent = Intent(this, className);
        intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        startActivity(intent)
    }

    fun toastLong(str : String) {
        Toast.makeText(this,str, Toast.LENGTH_LONG).show()
    }
    fun toastShort(str : String) {
        Toast.makeText(this,str, Toast.LENGTH_SHORT).show()
    }


    fun appFinish() {
        finishAndRemoveTask()
        finishAffinity()
    }

    // 리스너 생성
    interface OnBackPressedListener {
        fun onBack()
    }

    // 리스너 객체 생성
    var mBackListener: OnBackPressedListener? = null

    // 리스너 설정 메소드
    fun setOnBackPressedListener(listener: OnBackPressedListener) {
        mBackListener = listener
    }

    var pressedTime: Long = 0
    // 뒤로가기 버튼을 눌렀을 때의 오버라이드 메소드
    override fun onBackPressed() {

        // 다른 Fragment 에서 리스너를 설정했을 때 처리됩니다.
        if (mBackListener != null) {
            mBackListener!!.onBack()
            DLog().e("Listener is not null")
            // 리스너가 설정되지 않은 상태(예를들어 메인Fragment)라면
            // 뒤로가기 버튼을 연속적으로 두번 눌렀을 때 앱이 종료됩니다.
        } else {
            DLog().e("Listener is null")
            if (pressedTime.toInt() == 0) {
                toastShort("한 번 더 누르면 종료됩니다.")
                pressedTime = System.currentTimeMillis()
            } else {
                val seconds = (System.currentTimeMillis() - pressedTime).toInt()

                if (seconds > 2000) {
                    toastShort("한 번 더 누르면 종료됩니다.")
                    pressedTime = 0
                } else {
                    super.onBackPressed()
                    DLog().e("onBackPressed : finish, killProcess")
                    appFinish()
                }
            }
        }
    }
}