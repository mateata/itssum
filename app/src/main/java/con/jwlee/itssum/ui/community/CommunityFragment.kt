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
import con.jwlee.itssum.ui.RecyclerDeco
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.main_toolbar.*


class CommunityFragment : Fragment(), View.OnClickListener {

    var allList : ArrayList<GoodData> = ArrayList<GoodData>()
    var sortList : ArrayList<GoodData> = ArrayList<GoodData>()
    var foodList : ArrayList<GoodData> = ArrayList<GoodData>()
    var caffeList : ArrayList<GoodData> = ArrayList<GoodData>()
    var salonList : ArrayList<GoodData> = ArrayList<GoodData>()
    var goodMarketList : ArrayList<GoodData> = ArrayList<GoodData>()

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

        allList = setData(AppControl.sName,root)

        header_title.setText(getString(R.string.title_dashboard))
        goodList.addItemDecoration(RecyclerDeco(12))

        good_gubun_all.setOnClickListener(this)
        good_gubun_caffe.setOnClickListener(this)
        good_gubun_food.setOnClickListener(this)
        good_gubun_salon.setOnClickListener(this)
        good_check.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.good_gubun_all -> {selectSortBtn(0)}
            R.id.good_gubun_food -> {selectSortBtn(1)}
            R.id.good_gubun_caffe -> {selectSortBtn(2)}
            R.id.good_gubun_salon -> {selectSortBtn(3)}
            R.id.good_check -> {
                if (good_check.isChecked()) {
                    selectSortBtn(4)
                } else {
                    selectSortBtn(0)
                }
            }
        }
    }

    fun itemSort() {
        for(good in allList) {
            if(good.previous) { // 착한가게
                goodMarketList.add(good)
            }

            if(good.sector.equals("식사")) {
                foodList.add(good)
            } else if(good.sector.equals("제과.카페")) {
                caffeList.add(good)
            } else {
                salonList.add(good)
            }
        }
        DLog().e("itemSize = " + allList.size)
    }


    // 분류 버튼 뷰를 바꿔주고 선택한 분류를 보여준다
    fun selectSortBtn(idx : Int) {

        when(idx) {
            0 -> {
                sortList = allList
                good_gubun_all.background = this.requireContext().getDrawable(R.drawable.good_selected)
                good_gubun_food.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_caffe.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_salon.background = this.requireContext().getDrawable(R.drawable.line_outer)
            }
            1 -> {
                sortList = foodList
                good_gubun_all.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_food.background = this.requireContext().getDrawable(R.drawable.good_selected)
                good_gubun_caffe.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_salon.background = this.requireContext().getDrawable(R.drawable.line_outer)
            }
            2 -> {
                sortList = caffeList
                good_gubun_all.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_food.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_caffe.background = this.requireContext().getDrawable(R.drawable.good_selected)
                good_gubun_salon.background = this.requireContext().getDrawable(R.drawable.line_outer)
            }
            3 -> {
                sortList = salonList
                good_gubun_all.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_food.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_caffe.background = this.requireContext().getDrawable(R.drawable.line_outer)
                good_gubun_salon.background = this.requireContext().getDrawable(R.drawable.good_selected)
            }
            // 4번은 착한가게만 체크박스
            4 -> { sortList = salonList}
        }

        var adapter = GoodAdapter(sortList)
        goodList.adapter = adapter
        adapter.notifyDataSetChanged()
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
                        var sectorVal = sectorSort(data.get("sector").toString())

                        var goodVal : GoodData = GoodData(
                            sectorVal,
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
                } // for문 끝
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

                itemSort() // 미리 분류를 준비한다.
            } else {
                DLog().e("No such document")
            }
        }
            .addOnFailureListener { exception ->
                DLog().e("get failed with : ${exception} ")
            }
        return itemList
    }

    fun sectorSort(sector : String) : String {
        var sec : String = "식사"

        if(sector.equals("한식") || sector.equals("중식") || sector.equals("일식")) {
            sec = "식사"
        } else if(sector.startsWith("기타") || sector.startsWith("양식") || sector.equals("제과업")) {
            sec = "제과.카페"
        } else if(sector.endsWith("미용업") || sector.equals("세탁업") || sector.equals("체육시설업")) {
            sec = "편의시설"
        }

        return sec
    }


    companion object {

        fun newInstance(): CommunityFragment {
            return CommunityFragment()
        }
    }

}