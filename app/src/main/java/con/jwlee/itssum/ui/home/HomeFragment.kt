package con.jwlee.itssum.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import con.jwlee.itssum.R
import con.jwlee.itssum.data.Mvalue
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(con.jwlee.itssum.R.layout.fragment_home, container, false)
        return root
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

        root.nature_button.setOnClickListener {
/*            val intent = Intent(this.requireContext(), MarketListActivity::class.java)
            intent.putExtra("bigList", natureBigList)
            intent.putExtra("oldList", natureOldList)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)*/
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",natureBigList)
            bundle.putParcelableArrayList("oldList",natureOldList)

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

        root.process_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",processBigList)
            bundle.putParcelableArrayList("oldList",processOldList)

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

        root.indust_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",industBigList)
            bundle.putParcelableArrayList("oldList",industOldList)

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

        root.dining_button.setOnClickListener {
            var bundle = Bundle()
            bundle.putParcelableArrayList("bigList",diningBigList)
            bundle.putParcelableArrayList("oldList",diningOldList)

            findNavController().navigate(R.id.action_to_navigation_marketlist, bundleOf("listData" to bundle))
        }

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