package con.jwlee.itssum.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import con.jwlee.itssum.MainActivity
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.ui.BaseActivity
import kotlinx.android.synthetic.main.local_act.*

class SetLocalActivity : BaseActivity(), View.OnClickListener {

    lateinit var pref : SharedPreferences;
    lateinit var edit : SharedPreferences.Editor;
    var app = AppControl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.local_act)
        pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        edit = pref.edit()

        local_btn1.setOnClickListener(this)
        local_btn2.setOnClickListener(this)
        local_btn3.setOnClickListener(this)
        local_btn4.setOnClickListener(this)
        local_btn5.setOnClickListener(this)
        local_btn6.setOnClickListener(this)
        local_btn7.setOnClickListener(this)
        local_btn8.setOnClickListener(this)
        local_btn9.setOnClickListener(this)
    }

    var location = 1
    var locName : String = ""
    var locSale : String = ""

    override fun onClick(v: View) {
        locSale = getString(R.string.sale_incheon)
        when(v.id) {
            R.id.local_btn1 -> {
                location = 1
                locName = getString(R.string.guplace_middle)
            }
            R.id.local_btn2 -> {
                location = 2
                locName = getString(R.string.guplace_east)
            }
            R.id.local_btn3 -> {
                location = 3
                locName = getString(R.string.guplace_michuhol)
            }
            R.id.local_btn4 -> {
                location = 4
                locName = getString(R.string.guplace_yeonsu)
                locSale = getString(R.string.sale_yeonsu)
            }
            R.id.local_btn5 -> {
                location = 5
                locName = getString(R.string.guplace_southeast)
            }
            R.id.local_btn6 -> {
                location = 6
                locName = getString(R.string.guplace_bupyeong)
            }
            R.id.local_btn7 -> {
                location = 7
                locName = getString(R.string.guplace_geyang)
            }
            R.id.local_btn8 -> {
                location = 8
                locName = getString(R.string.guplace_west)
                locSale = getString(R.string.sale_west)
            }
            R.id.local_btn9 -> {
                location = 9
                locName = getString(R.string.guplace_incheon)
            }
        }


        app.sLocation = location
        app.sName = locName
        app.sSale = locSale

        edit.putInt(app.locationKey, location).apply()
        edit.putString(app.saleKey,locSale).apply()
        edit.putString(app.nameKey,locName).apply()
        toastLong("${locName} : ${locSale} " + getString(R.string.local_saleintro))
        startNextActivity(MainActivity::class.java)
    }



}