package con.jwlee.itssum.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
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
import kotlinx.android.synthetic.main.main_toolbar.*


class CommunityFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    fun init(root : View) {
        val linearLayoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL,false)
        root.goodList.layoutManager = linearLayoutManager

        setData(AppControl.sName,root)

        header_title.setText(getString(R.string.title_dashboard))

    }

    // Firebase Firestore 에서 데이터 읽어와서 Set
    fun setData(guPlace : String, view : View) : ArrayList<GoodData> {

        var itemList = ArrayList<GoodData>()

        val bigDB= db.collection("gooddata").whereEqualTo("place",guPlace).get()
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
                adapter.setOnItemClickListener(object : GoodAdapter.OnItemClickListener {
                    override fun onItemClick(v: View, position: Int, data: GoodData) {
                        DLog().e(data.toString())
                        var bundle = Bundle()
                        bundle.putSerializable("goodData", data)
                        findNavController().navigate(R.id.navigation_gooddetail, bundleOf("detailData" to bundle))
                    }
                })
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


    companion object {

        fun newInstance(): CommunityFragment {
            return CommunityFragment()
        }
    }

}