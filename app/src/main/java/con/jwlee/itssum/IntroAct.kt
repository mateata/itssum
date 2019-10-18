package con.jwlee.itssum

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.content.SharedPreferences
import com.google.firebase.firestore.FirebaseFirestore
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.data.MarketParser
import con.jwlee.itssum.util.DLog


class IntroAct : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var pref : SharedPreferences;
    lateinit var edit : SharedPreferences.Editor;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_act)

        pref = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        edit = pref.edit()

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


        dbUpdate()
    }

    fun dbUpdate() {
        val bigDBver = pref.getInt(AppControl().bigDBKey,0);
        val oldDBver = pref.getInt(AppControl().oldDBKey,0);
        val expDBver = pref.getInt(AppControl().expDBKey,0);
        DLog().e("DB VER ${bigDBver} / ${oldDBver} / ${expDBver} ")

        //대형마트 xml파싱, Firestore에 업데이트
        if(bigDBver < AppControl().bigDBversion) {
            val marParser = MarketParser()
            val itemList = marParser.mParser(marParser.bigXml, this)

            for(mVal in itemList) {
                db.collection("bigmarket").document(mVal.item)
                    .set(mVal)
                    .addOnSuccessListener { documentReference ->
                        DLog().e(" added with ID: ${mVal.item}")
                    }
                    .addOnFailureListener { err ->
                        DLog().e("Error adding document : ${err}" )
                    }
            }
            edit.putInt(AppControl().bigDBKey, AppControl().bigDBversion) // 작업 완료후 새 버전을 pref에 저장
            edit.apply()
        }

        //재래시장 xml파싱, Firestore에 업데이트
        if(oldDBver <= AppControl().oldDBversion) {
            val marParser = MarketParser()
            val itemList = marParser.mParser(marParser.oldXml, this)

            for(mVal in itemList) {
                db.collection("oldmarket").document(mVal.item)
                    .set(mVal)
                    .addOnSuccessListener { documentReference ->
                        DLog().e(" added with ID: ${mVal.item}")
                    }
                    .addOnFailureListener { err ->
                        DLog().e("Error adding document : ${err}" )
                    }
            }
            edit.putInt(AppControl().oldDBKey, AppControl().oldDBversion) // 작업 완료후 새 버전을 pref에 저장
            edit.apply()
        }

        //기업형 슈퍼 xml파싱, Firestore에 업데이트
        if(expDBver < AppControl().expDBversion) {
            val marParser = MarketParser()
            val itemList = marParser.mParser(marParser.expXml, this)

            for(mVal in itemList) {
                db.collection("expmarket").document(mVal.item)
                    .set(mVal)
                    .addOnSuccessListener { documentReference ->
                        DLog().e(" added with ID: ${mVal.item}")
                    }
                    .addOnFailureListener { err ->
                        DLog().e("Error adding document : ${err}" )
                    }
            }
            edit.putInt(AppControl().expDBKey, AppControl().expDBversion) // 작업 완료후 새 버전을 pref에 저장
            edit.apply()
        }
    }
}