package con.jwlee.itssum

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import con.jwlee.itssum.ui.BaseActivity
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_toolbar.*


class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)

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
                Snackbar.make(
                    container,
                    "한 번 더 누르면 종료됩니다.", Snackbar.LENGTH_LONG
                ).show()
                pressedTime = System.currentTimeMillis()
            } else {
                val seconds = (System.currentTimeMillis() - pressedTime).toInt()

                if (seconds > 2000) {
                    Snackbar.make(
                        container,
                        " 한 번 더 누르면 종료됩니다.", Snackbar.LENGTH_LONG
                    ).show()
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
