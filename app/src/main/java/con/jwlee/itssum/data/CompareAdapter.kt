package con.jwlee.itssum.data

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.R
import kotlinx.android.synthetic.main.market_item.view.*


class CompareAdapter(private val items : ArrayList<MData>) : RecyclerView.Adapter<CompareAdapter.ItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        items[position].let { data ->
            with(holder) {
                val listener = View.OnClickListener {it ->
                    Toast.makeText(it.context, "상세 : ${data.name}", Toast.LENGTH_SHORT).show()
                }
                itemName.setOnClickListener(listener)
                itemName.setText(data.item)

                var big =data.bigVal
                var old = data.oldVal

                // 1 : 중구, 2 : 동구, 3 : 미추홀구, 4 : 연수구, 5: 남동구, 6: 부평구, 7: 계양구, 8: 서구

                // old(적립가능 매장)값에 지역별 할인률을 차감하여 값을 다시 set
                when (AppControl().setLocation) {
                    4 -> old = (old * AppControl().yeonsuCashbag).toInt() // 연수구 : 10%
                    8 -> old = (old * AppControl().westCashbag).toInt() // 서구 : 7%
                    else -> old = (old * AppControl().incheonCashbag).toInt() // 기타 인천지역 : 3%
                }

                bigValue.setText(big.toString())
                oldValue.setText(old.toString())

                // 양쪽의 가격을 비교하여, 저렴한쪽에 색을 칠한다
                if(big < old) {
                    bigValue.setTextColor(Color.BLUE)
                    oldValue.setTextColor(Color.BLACK)
                } else {
                    oldValue.setTextColor(Color.BLUE)
                    bigValue.setTextColor(Color.BLACK)
                }
            }
        }

    }


    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.market_item, parent, false)) {
        val itemName = itemView.item_name
        val bigValue = itemView.big_val
        val oldValue = itemView.old_val
    }
}