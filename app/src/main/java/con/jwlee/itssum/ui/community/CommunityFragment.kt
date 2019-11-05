package con.jwlee.itssum.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.data.GoodAdapter
import con.jwlee.itssum.data.GoodData
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class CommunityFragment : Fragment() {

    private lateinit var dashboardViewModel: CommunityViewModel
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(CommunityViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        init(root)
        return root
    }

    fun init(root : View) {
        val linearLayoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL,false)
        root.goodList.layoutManager = linearLayoutManager

        // 지역 설정
        var guPlace = getString(R.string.guplace_east)
        when(AppControl().setLocation) {
            1 -> { guPlace = getString(R.string.guplace_middle) }
            2 -> { guPlace = getString(R.string.guplace_east) }
            3 -> { guPlace = getString(R.string.guplace_michuhol) }
            4 -> { guPlace = getString(R.string.guplace_yeonsu) }
            5 -> { guPlace = getString(R.string.guplace_southeast) }
            6 -> { guPlace = getString(R.string.guplace_bupyeong) }
            7 -> { guPlace = getString(R.string.guplace_geyang) }
            8 -> { guPlace = getString(R.string.guplace_west) }
        }
        val itemList = setData(guPlace,root)




    }

    // Firebase Firestore 에서 데이터 읽어와서 Set
    fun setData(guPlace : String, view : View) : ArrayList<GoodData> {

        var itemList = ArrayList<GoodData>()

        val bigDB= db.collection("gooddata").get() //.whereEqualTo("place",guPlace).get()
        bigDB.addOnSuccessListener { document ->
            if (document != null) {
                for (docSnapshot : DocumentSnapshot in document) { // for문을 돌면서 Mvalue객체를 만들고 이를 itemList에 구성
                    //DLog().e("${docSnapshot.id} => ${docSnapshot.data}")
                    val data = docSnapshot.data
                    if (data != null) {
                        // 데이터 자동전환이 안되어서 수동으로 Set
                        var goodVal : GoodData = GoodData(
                            data.get("sector").toString(),
                            data.get("name").toString(),
                            data.get("place").toString(),
                            data.get("address").toString(),
                            data.get("phone").toString(),
                            data.get("intro").toString(),
                            data.get("time").toString(),
                            data.get("delivery").toString().toBoolean(),
                            data.get("parking").toString().toBoolean(),
                            data.get("previous").toString().toBoolean(),
                            data.get("favorite").toString().toBoolean(),
                            data.get("itemName1").toString(),
                            data.get("itemval1").toString().toInt(),
                            data.get("itemName2").toString(),
                            data.get("itemval2").toString().toInt(),
                            data.get("itemName3").toString(),
                            data.get("itemval3").toString().toInt())
                        itemList.add(goodVal)
                        DLog().e("goodData : ${goodVal.name} => ${goodVal.toString()}")
                    }
                }
                var adapter = GoodAdapter(itemList)
                view.goodList.adapter = adapter
                adapter.notifyDataSetChanged()
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