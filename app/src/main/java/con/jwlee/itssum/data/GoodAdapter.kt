package con.jwlee.itssum.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.R
import kotlinx.android.synthetic.main.good_item.view.*


class GoodAdapter(private val items : ArrayList<GoodData>) : RecyclerView.Adapter<GoodAdapter.ItemViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(parent)


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        items[position].let { data ->
            with(holder) {
                itemView.setOnClickListener { v ->
                    if (position != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        mListener?.onItemClick(v, position, data)
                    }
                }
                itemName.setText(data.name)
                itemAddr.setText(data.address)
                itemPhone.setText(data.phone)
                if(data.favorite) {
                    favorite.setImageResource(R.drawable.bt_favorite2)
                }
            }
        }

    }


    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.good_item, parent, false)) {
        val itemName = itemView.item_name
        val itemAddr = itemView.item_address
        val itemPhone = itemView.item_phone
        val favorite = itemView.favorite_check
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, position: Int, data : GoodData)
    }

    // 리스너 객체 참조를 저장하는 변수
    private var mListener: OnItemClickListener? = null

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }
}