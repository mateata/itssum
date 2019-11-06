package con.jwlee.itssum.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.data.CompareAdapter
import con.jwlee.itssum.data.MData
import con.jwlee.itssum.data.Mvalue
import con.jwlee.itssum.ui.menu.MenuViewModel
import kotlinx.android.synthetic.main.market_list_ac.*

class MarketListFragment : Fragment() {

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
        init(view)
    }

    fun init(view : View) {

        val bundle = arguments?.getBundle("listData")
        lateinit var bigList : ArrayList<Mvalue>
        lateinit var oldList : ArrayList<Mvalue>

        if(bundle != null ) {
            bigList = bundle.getParcelableArrayList<Mvalue>("bigList") as ArrayList<Mvalue>
            oldList = bundle.getParcelableArrayList<Mvalue>("oldList") as ArrayList<Mvalue>
        }

        val linearLayoutManager = LinearLayoutManager(this.requireContext(), RecyclerView.VERTICAL,false)
        marketTable.layoutManager = linearLayoutManager



        var itemList = calcData(bigList,oldList)


        var adapter = CompareAdapter(itemList)
        marketTable.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    // 대형마트 데이터와 재래시장 데이터를 가져와서 가공한다.
    fun calcData (bigList : ArrayList<Mvalue>, oldList : ArrayList<Mvalue>) : ArrayList<MData> {
        var calcList = ArrayList<MData>()

        val app = AppControl()


        var oldVal = 0
        var bigVal = 0

        for (i in bigList.indices) {
            var data = bigList[i]
            var data2 = oldList[i]

            var itemName = data.item
            var detailName = data.name

            // 1 : 중구, 2 : 동구, 3 : 미추홀구, 4 : 연수구, 5: 남동구, 6: 부평구, 7: 계양구, 8: 서구
            // 만약 해당항목의 값이 0이라면 평균값을 넣는다
            when (app.setLocation) {
                1 -> { if(data.middle == 0) bigVal = data.average else bigVal = data.middle }
                2 -> { if(data.east == 0) bigVal = data.average else bigVal = data.east }
                3 -> { if(data.michuhol == 0) bigVal = data.average else bigVal = data.michuhol }
                4 -> { if(data.yeonsu == 0) bigVal = data.average else bigVal = data.yeonsu }
                5 -> { if(data.southeast == 0) bigVal = data.average else bigVal = data.southeast }
                6 -> { if(data.bupyeong == 0) bigVal = data.average else bigVal = data.bupyeong }
                7 -> { if(data.geyang == 0) bigVal = data.average else bigVal = data.geyang }
                8 -> { if(data.west == 0) bigVal = data.average else bigVal = data.west }
            }
            when (app.setLocation) {
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
    companion object {

        fun newInstance(): MarketListFragment {
            return MarketListFragment()
        }
    }
}