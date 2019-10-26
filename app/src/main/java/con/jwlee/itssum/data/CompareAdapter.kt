package con.jwlee.itssum.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.R
import con.jwlee.itssum.data.CompareAdapter.ItemViewHolder
import kotlinx.android.synthetic.main.market_item.view.*


class CompareAdapter(private val items : ArrayList<Mvalue>) : RecyclerView.Adapter<CompareAdapter.ItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CompareAdapter.ItemViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.market_item, parent, false)
        return ItemViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: CompareAdapter.ItemViewHolder, position: Int) {
        val item = items.get(position)
        val listener = View.OnClickListener {it ->
            Toast.makeText(it.context, "Clicked: ${item.item}", Toast.LENGTH_SHORT).show()
        }
        holder.onBind(listener, item)
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun onBind(listener: View.OnClickListener, data: Mvalue) {

            val app = AppControl()
            var price = 0
            itemView.setOnClickListener(listener)

            itemView.item_name.setText(data.item)
            // 1 : 중구, 2 : 동구, 3 : 미추홀구, 4 : 연수구, 5: 남동구, 6: 부평구, 7: 계양구, 8: 서구
            // 만약 해당항목의 값이 0이라면 평균값을 넣는다
            when(app.mGubun) {
                0 -> { // 대형마트
                    when (app.setLocation) {
                        1 -> { if(data.middle == 0) price = data.average else price = data.middle}
                        2 -> { if(data.east == 0) price = data.average else price = data.east}
                        3 -> { if(data.michuhol == 0) price = data.average else price = data.michuhol}
                        4 -> { if(data.yeonsu == 0) price = data.average else price = data.yeonsu}
                        5 -> { if(data.southeast == 0) price = data.average else price = data.southeast}
                        6 -> { if(data.bupyeong == 0) price = data.average else price = data.bupyeong}
                        7 -> { if(data.geyang == 0) price = data.average else price = data.geyang}
                        8 -> { if(data.west == 0) price = data.average else price = data.west}
                    }
                    itemView.big_val.setText(price.toString())
                    itemView.old_val.setText(price.toString())
                }
                1 -> { // 재래시장 :
                    when (app.setLocation) {
                        1 -> { if(data.middle == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.middle * app.incheonCashbag).toInt() }
                        2 -> { if(data.east == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.east * app.incheonCashbag).toInt()}
                        3 -> { if(data.michuhol == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.michuhol * app.incheonCashbag).toInt()}
                        4 -> { if(data.yeonsu == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.yeonsu * app.incheonCashbag).toInt()}
                        5 -> { if(data.southeast == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.southeast * app.incheonCashbag).toInt()}
                        6 -> { if(data.bupyeong == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.bupyeong * app.incheonCashbag).toInt()}
                        7 -> { if(data.geyang == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.geyang * app.incheonCashbag).toInt()}
                        8 -> { if(data.west == 0) price = (data.average * app.incheonCashbag).toInt() else price = (data.west * app.incheonCashbag).toInt()}
                    }
                    itemView.old_val.setText(price.toString())
                }
            }

        }
    }

}