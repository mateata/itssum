package con.jwlee.itssum.data

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import con.jwlee.itssum.R


class CompareAdapter : RecyclerView.Adapter<CompareAdapter.ItemViewHolder>() {

    private val listData = ArrayList<Mvalue>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompareAdapter.ItemViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: CompareAdapter.ItemViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addItem(data: Mvalue) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data)
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemName: TextView = itemView.findViewById(R.id.item_name)
        var bigVal: TextView = itemView.findViewById(R.id.big_val)
        var oldVal: TextView = itemView.findViewById(R.id.old_val)

        fun onBind(data: Mvalue) {

            itemName.setText(data.item)
            bigVal.setText(data.bupyeong)
            oldVal.setText(data.middle)
        }
    }

}