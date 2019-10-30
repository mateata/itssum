package con.jwlee.itssum.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import con.jwlee.itssum.data.Mvalue
import con.jwlee.itssum.util.DLog

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val db = FirebaseFirestore.getInstance()
    val itemList = setData("bigmarket")

    fun setData(colName : String) : ArrayList<Mvalue> {
        // Firebase Firestore 에서 데이터 읽어와서 Set

        var itemList = ArrayList<Mvalue>()

        val bigDB= db.collection(colName).get().addOnSuccessListener { document ->
            if (document != null) {
                for (docSnapshot : DocumentSnapshot in document) { // for문을 돌면서 Mvalue객체를 만들고 이를 itemList에 구성
                    //DLog().e("${docSnapshot.id} => ${docSnapshot.data}")
                    val data = docSnapshot.data
                    if (data != null) {
                        var mVal : Mvalue = Mvalue(
                            data.get("gubun").toString(),
                            data.get("item").toString(),
                            data.get("name").toString(),
                            data.get("middle").toString().toInt(),
                            data.get("east").toString().toInt(),
                            data.get("michuhol").toString().toInt(),
                            data.get("yeonsu").toString().toInt(),
                            data.get("southeast").toString().toInt(),
                            data.get("bupyeong").toString().toInt(),
                            data.get("geyang").toString().toInt(),
                            data.get("west").toString().toInt(),
                            data.get("average").toString().toInt())
                        itemList.add(mVal)
                        DLog().e("MVList : ${mVal.item} => ${mVal.toString()}")
                    }
                }
            } else {
                DLog().e("No such document")
            }
        }
            .addOnFailureListener { exception ->
                DLog().e("get failed with : ${exception} ")
            }
        return itemList
    }
}