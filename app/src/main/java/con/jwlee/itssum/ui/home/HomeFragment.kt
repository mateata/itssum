package con.jwlee.itssum.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.data.Mvalue
import con.jwlee.itssum.ui.BaseActivity
import con.jwlee.itssum.ui.BaseFragment
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.main_toolbar.*


class HomeFragment : BaseFragment() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(con.jwlee.itssum.R.layout.fragment_home, container, false)
        return root
    }

    override fun onBack() {
        super.onBack()
        BaseActivity().onBackPressed()
    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }


    fun init(root : View) {

        val natureBigList = setData("bigmarket",getString(R.string.dbgubun_nature))
        val natureOldList = setData("oldmarket",getString(R.string.dbgubun_nature))

        val processBigList = setData("bigmarket",getString(R.string.dbgubun_process))
        val processOldList = setData("oldmarket",getString(R.string.dbgubun_process))

        val industBigList = setData("bigmarket",getString(R.string.dbgubun_industrial))
        val industOldList = setData("oldmarket",getString(R.string.dbgubun_industrial))

        val diningBigList = setData("bigmarket",getString(R.string.dbgubun_dining))
        val diningOldList = setData("oldmarket",getString(R.string.dbgubun_dining))

        //농축수산물 데이터로 이동
        root.nature_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",natureBigList)
            bundle.putParcelableArrayList("oldList",natureOldList)
            bundle.putString("category", getString(R.string.market_gubun1))

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

        //가공식품 데이터로 이동
        root.process_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",processBigList)
            bundle.putParcelableArrayList("oldList",processOldList)
            bundle.putString("category", getString(R.string.market_gubun2))

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

        //공산품 데이터로 이동
        root.indust_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",industBigList)
            bundle.putParcelableArrayList("oldList",industOldList)
            bundle.putString("category", getString(R.string.market_gubun3))

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

        //외식비 데이터로 이동
        root.dining_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",diningBigList)
            bundle.putParcelableArrayList("oldList",diningOldList)
            bundle.putString("category", getString(R.string.market_gubun4))

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

        // 리스트 우선 세팅하고 나머지 세부 뷰

        header_title.setText(R.string.title_home)
        home_desc_market.setText(AppControl.sName + " " + getString(R.string.home_desc_market))
        home_desc_good.setText(AppControl.sName + " " + getString(R.string.home_desc_good))

        home_desc_temp.setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
        }
        home_desc_good.setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
        }
        bt_search.visibility = View.GONE

    }

    // Firebase Firestore 에서 데이터 읽어와서 Set
    fun setData(colName : String, dataGubun : String) : ArrayList<Mvalue> {

        var itemList = ArrayList<Mvalue>()

        val bigDB= db.collection(colName).whereEqualTo("gubun",dataGubun).get()
            bigDB.addOnSuccessListener { document ->
                if (document != null) {
                    for (docSnapshot : DocumentSnapshot in document) { // for문을 돌면서 Mvalue객체를 만들고 이를 itemList에 구성
                        //DLog().e("${docSnapshot.id} => ${docSnapshot.data}")
                        val data = docSnapshot.data
                        if (data != null) {
                            // 데이터 자동전환이 안되어서 수동으로 Set
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
                            DLog().e("${colName} : ${mVal.item} => ${mVal.toString()}")
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