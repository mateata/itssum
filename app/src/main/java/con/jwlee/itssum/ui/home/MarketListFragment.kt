package con.jwlee.itssum.ui.home

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.data.CompareAdapter
import con.jwlee.itssum.data.MData
import con.jwlee.itssum.data.Mvalue
import con.jwlee.itssum.ui.BaseFragment
import con.jwlee.itssum.ui.RecyclerDeco
import con.jwlee.itssum.util.Util
import kotlinx.android.synthetic.main.good_toolbar.*
import kotlinx.android.synthetic.main.main_toolbar.*
import kotlinx.android.synthetic.main.main_toolbar.bt_back
import kotlinx.android.synthetic.main.main_toolbar.bt_search
import kotlinx.android.synthetic.main.main_toolbar.header_title
import kotlinx.android.synthetic.main.market_list_ac.*

class MarketListFragment : BaseFragment() {

    lateinit var mContext : Context
    lateinit var imm : InputMethodManager
    lateinit var itemList : ArrayList<MData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.market_list_ac, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = this.requireContext()
        init()
    }

    fun init() {

        val bundle = arguments?.getBundle("listData")
        lateinit var bigList : ArrayList<Mvalue>
        lateinit var oldList : ArrayList<Mvalue>
        var categoryName = getString(R.string.market_gubun1)

        if(bundle != null ) {
            bigList = bundle.getParcelableArrayList<Mvalue>("bigList") as ArrayList<Mvalue>
            oldList = bundle.getParcelableArrayList<Mvalue>("oldList") as ArrayList<Mvalue>
            categoryName = bundle.getString("category", getString(R.string.market_gubun1))
        }

        val linearLayoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL,false)
        marketTable.layoutManager = linearLayoutManager

        itemList = calcData(bigList,oldList)

        // 가격비교 List Set
        var adapter = CompareAdapter(itemList,mContext)
        marketTable.adapter = adapter
        (marketTable.adapter as CompareAdapter).notifyDataSetChanged()
        marketTable.addItemDecoration(RecyclerDeco(12))

        bt_back.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        // 타이틀 및 글자 Set
        header_title.setText(R.string.title_home)
        cate_name.setText(categoryName)
        cate_sale.setText(AppControl.sSale + " " + getString(R.string.sale_per_desc))

        imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        bt_search.setOnClickListener {
            searchBtn()
        }
    }

    // 대형마트 데이터와 재래시장 데이터를 가져와서 가공한다.
    fun calcData (bigList : ArrayList<Mvalue>, oldList : ArrayList<Mvalue>) : ArrayList<MData> {
        var calcList = ArrayList<MData>()

        val app = AppControl


        var oldVal = 0
        var bigVal = 0

        for (i in bigList.indices) {
            var data = bigList[i]
            var data2 = oldList[i]

            var itemName = data.item
            var detailName = data.name

            // 1 : 중구, 2 : 동구, 3 : 미추홀구, 4 : 연수구, 5: 남동구, 6: 부평구, 7: 계양구, 8: 서구
            // 만약 해당항목의 값이 0이라면 평균값을 넣는다
            when (app.sLocation) {
                1 -> { if(data.middle == 0) bigVal = data.average else bigVal = data.middle }
                2 -> { if(data.east == 0) bigVal = data.average else bigVal = data.east }
                3 -> { if(data.michuhol == 0) bigVal = data.average else bigVal = data.michuhol }
                4 -> { if(data.yeonsu == 0) bigVal = data.average else bigVal = data.yeonsu }
                5 -> { if(data.southeast == 0) bigVal = data.average else bigVal = data.southeast }
                6 -> { if(data.bupyeong == 0) bigVal = data.average else bigVal = data.bupyeong }
                7 -> { if(data.geyang == 0) bigVal = data.average else bigVal = data.geyang }
                8 -> { if(data.west == 0) bigVal = data.average else bigVal = data.west }
            }
            when (app.sLocation) {
                1 -> { if(data2.middle == 0) oldVal = data2.average else oldVal = data2.middle }
                2 -> { if(data2.east == 0) oldVal = data2.average else oldVal = data2.east }
                3 -> { if(data2.michuhol == 0) oldVal = data2.average else oldVal = data2.michuhol }
                4 -> { if(data2.yeonsu == 0) oldVal = data2.average else oldVal = data2.yeonsu }
                5 -> { if(data2.southeast == 0) oldVal = data2.average else oldVal = data2.southeast }
                6 -> { if(data2.bupyeong == 0) oldVal = data2.average else oldVal = data2.bupyeong }
                7 -> { if(data2.geyang == 0) oldVal = data2.average else oldVal = data2.geyang }
                8 -> { if(data2.west == 0) oldVal = data2.average else oldVal = data2.west }
            }

            var mData : MData = MData(itemName,detailName,bigVal,oldVal)
            calcList.add(mData)
        }

        return calcList
    }

    fun searchBtn() {
        searchEt.visibility = View.VISIBLE
        searchEt.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            // TODO : keypad 에서 enter 실행시 Listen하고 동작할 액션을 작성
            override fun onEditorAction(
                textView: TextView,
                i: Int,
                keyEvent: KeyEvent?
            ): Boolean { // 텍스트 내용을 가져온다.
                val searchData = textView.text.toString()
                // 텍스트 내용이 비어있다면...
                if (searchData.isEmpty()) { // 토스트 메세지를 띄우고, 창 내용을 비운다
                    Util().toastLong(mContext,"정보를 입력해주세요")
                    textView.clearFocus()
                    textView.isFocusable = false
                    textView.isFocusableInTouchMode = true
                    textView.isFocusable = true
                    return true
                }
                when (i) {
                    EditorInfo.IME_ACTION_DONE -> {
                        searchComplete(searchData)
                    }
                    else ->  // TODO : Write Your Code
                        return false
                }
                // 내용 비우고 다시 이벤트 할수있게 선택
                textView.clearFocus()
                textView.isFocusable = false
                textView.text = ""
                textView.isFocusableInTouchMode = true
                textView.isFocusable = true
                return true
            }
        })
    }

    fun searchComplete(txt : String) {
        var searchList = ArrayList<MData>()
        for(mdata in itemList) {
            if(mdata.item.contains(txt)) {
                searchList.add(mdata)
            }
        }

        if(searchList.size == 0) {
            Util().toastLong(mContext,mContext.getString(R.string.search_none))
        } else {
            var adapter = CompareAdapter(searchList,mContext)
            marketTable.adapter = adapter
            (marketTable.adapter as CompareAdapter).notifyDataSetChanged()
            marketTable.addItemDecoration(RecyclerDeco(12))
            imm.hideSoftInputFromWindow(searchEt.windowToken, 0)
            searchEt.visibility = View.GONE
        }
    }

    override fun onBack() {
        findNavController().navigate(R.id.navigation_home)
    }


    companion object {

        fun newInstance(): MarketListFragment {
            return MarketListFragment()
        }
    }
}