package con.jwlee.itssum

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.data.CommunityParser
import con.jwlee.itssum.data.MarketParser
import con.jwlee.itssum.ui.splash.SplashActivity
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.intro_act.*
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget




class IntroAct : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var pref : SharedPreferences;
    lateinit var edit : SharedPreferences.Editor;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_act)

        pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        edit = pref.edit()

        // 지역설정을 불러낸다. (기본값 : 연수구 = 4)
        AppControl().setLocation = pref.getInt(AppControl().locationKey,1)
        AppControl().setSale = pref.getString(AppControl().saleKey,"10%").toString()
        AppControl().setName = pref.getString(AppControl().nameKey,getString(R.string.guplace_yeonsu)).toString()

        window.decorView.postDelayed({
            val intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
            // 액티비티 이동시 페이드인/아웃 효과를 보여준다. 즉, 인트로 화면이 부드럽게 사라진다.
            overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }, 3500)

        val ivTarget = GlideDrawableImageViewTarget(introImage)
        Glide.with(this).load(R.raw.intro_gif).into(ivTarget)
        dbUpdate()
    }

    fun dbUpdate() {
        val app = AppControl()

        val bigDBver = pref.getInt(app.bigDBKey,0);
        val oldDBver = pref.getInt(app.oldDBKey,0);
        val expDBver = pref.getInt(app.expDBKey,0);
        val goodDBver = pref.getInt(app.goodDBKey,0);
        DLog().e("DB VER ${bigDBver} / ${oldDBver} / ${expDBver} / ${goodDBver}")

        //대형마트 xml파싱, Firestore에 업데이트
        if(bigDBver < app.bigDBversion) {
            val marParser = MarketParser()
            val itemList = marParser.mParser(marParser.bigXml, this)

            for(mVal in itemList) {
                db.collection("bigmarket").document(mVal.item)
                    .set(mVal)
                    .addOnSuccessListener {
                        DLog().e(" added with ID: ${mVal.item}")
                    }
                    .addOnFailureListener { err ->
                        DLog().e("Error adding document : ${err}" )
                    }
            }
            edit.putInt(app.bigDBKey, app.bigDBversion) // 작업 완료후 새 버전을 pref에 저장
            edit.apply()
        }

        //재래시장 xml파싱, Firestore에 업데이트
        if(oldDBver < app.oldDBversion) {
            val marParser = MarketParser()
            val itemList = marParser.mParser(marParser.oldXml, this)

            for(mVal in itemList) {
                db.collection("oldmarket").document(mVal.item)
                    .set(mVal)
                    .addOnSuccessListener {
                        DLog().e(" added with ID: ${mVal.item}")
                    }
                    .addOnFailureListener { err ->
                        DLog().e("Error adding document : ${err}" )
                    }
            }
            edit.putInt(app.oldDBKey, app.oldDBversion) // 작업 완료후 새 버전을 pref에 저장
            edit.apply()
        }

        //기업형 슈퍼 xml파싱, Firestore에 업데이트
        if(expDBver < app.expDBversion) {
            val marParser = MarketParser()
            val itemList = marParser.mParser(marParser.expXml, this)

            for(mVal in itemList) {
                db.collection("expmarket").document(mVal.item)
                    .set(mVal)
                    .addOnSuccessListener {
                        DLog().e(" added with ID: ${mVal.item}")
                    }
                    .addOnFailureListener { err ->
                        DLog().e("Error adding document : ${err}" )
                    }
            }
            edit.putInt(app.expDBKey, app.expDBversion) // 작업 완료후 새 버전을 pref에 저장
            edit.apply()
        }

        if(goodDBver < app.goodDBversion) {
            val comParser = CommunityParser()
            val itemList = comParser.goodParser(this)

            for(goodData in itemList) {
                db.collection("gooddata").document(goodData.name)
                    .set(goodData)
                    .addOnSuccessListener {
                        DLog().e(" added with ID: ${goodData.name}")
                    }
                    .addOnFailureListener { err ->
                        DLog().e("Error adding document : ${err}" )
                    }
            }
            edit.putInt(app.goodDBKey, app.goodDBversion) // 작업 완료후 새 버전을 pref에 저장
            edit.apply()
        }
    }
}