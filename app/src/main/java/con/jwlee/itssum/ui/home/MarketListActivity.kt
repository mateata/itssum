package con.jwlee.itssum.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.data.CompareAdapter
import con.jwlee.itssum.data.MData
import con.jwlee.itssum.data.Mvalue
import kotlinx.android.synthetic.main.market_list_ac.*

class MarketListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.market_list_ac)

        val bigList : ArrayList<Mvalue> = intent.getParcelableArrayListExtra("bigList")
        val oldList : ArrayList<Mvalue> = intent.getParcelableArrayListExtra("oldList")

        val linearLayoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
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

}