package con.jwlee.itssum.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.R
import con.jwlee.itssum.util.Util
import kotlinx.android.synthetic.main.market_item.view.*


class CompareAdapter(private val items : ArrayList<MData>, context: Context) : RecyclerView.Adapter<CompareAdapter.ItemViewHolder>() {

    private var mContext : Context = context

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        items[position].let { data ->
            with(holder) {
                val listener = View.OnClickListener {it ->
                    Toast.makeText(it.context, "${data.item} : ${data.name}", Toast.LENGTH_SHORT).show()
                }
                itemName.setOnClickListener(listener)
                itemName.setText(data.item)

                var big =data.bigVal
                var old = data.oldVal
                var bName : String = mContext.getString(R.string.market_bigname_michuhol)
                var oName : String = mContext.getString(R.string.market_oldname_michuhol)
                // 1 : 중구, 2 : 동구, 3 : 미추홀구, 4 : 연수구, 5: 남동구, 6: 부평구, 7: 계양구, 8: 서구

                // old(적립가능 매장)값에 지역별 할인률을 차감하여 값을 다시 set
                when (AppControl.sLocation) {
                    1 -> { // 중구
                        old = (old * AppControl.incheonCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_middle)
                        oName = mContext.getString(R.string.market_oldname_middle)
                    }
                    2 -> { // 동구
                        old = (old * AppControl.incheonCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_east)
                        oName = mContext.getString(R.string.market_oldname_east)
                    }
                    3 -> { // 미추홀구
                        old = (old * AppControl.incheonCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_michuhol)
                        oName = mContext.getString(R.string.market_oldname_michuhol)
                    }
                    4 -> { // 연수구 : 10%
                        old = (old * AppControl.yeonsuCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_yeonsu)
                        oName = mContext.getString(R.string.market_oldname_yeonsu)
                    }
                    5 -> { // 남동구
                        old = (old * AppControl.incheonCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_southeast)
                        oName = mContext.getString(R.string.market_oldname_southeast)
                    }
                    6 -> { // 부평구
                        old = (old * AppControl.incheonCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_bupyeong)
                        oName = mContext.getString(R.string.market_oldname_bupyeong)
                    }
                    7 -> { // 계양구
                        old = (old * AppControl.incheonCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_geyang)
                        oName = mContext.getString(R.string.market_oldname_geyang)
                    }
                    8 -> { // 서구 : 7%
                        old = (old * AppControl.westCashbag).toInt()
                        bName = mContext.getString(R.string.market_bigname_west)
                        oName = mContext.getString(R.string.market_oldname_west)
                    }
                    else -> old = (old * AppControl.incheonCashbag).toInt() // 기타 인천지역 : 3%
                }

                bigValue.setText(Util().commaNumber(big))
                oldValue.setText(Util().commaNumber(old))
                bigName.setText(bName)
                oldName.setText(oName)

                // 양쪽의 가격을 비교하여, 저렴한쪽에 색을 칠한다
                if(big < old) {
                    bigValue.setTextColor(mContext.getColor(R.color.Ieum_chp_color))
                    oldValue.setTextColor(mContext.getColor(R.color.Ieum_exp_color))
                } else {
                    oldValue.setTextColor(mContext.getColor(R.color.Ieum_chp_color))
                    bigValue.setTextColor(mContext.getColor(R.color.Ieum_exp_color))
                }
            }
        }

    }


    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.market_item, parent, false)) {
        val itemName = itemView.item_name
        val bigValue = itemView.big_val
        val oldValue = itemView.old_val
        val bigName = itemView.big_name
        val oldName = itemView.old_name
    }
}