package con.jwlee.itssum.ui

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
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
        startActivity(intent)
    }


}