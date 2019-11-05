package con.jwlee.itssum.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
                val listener = View.OnClickListener { it ->
                    Toast.makeText(it.context, "상세 : ${data.name}", Toast.LENGTH_SHORT).show()
                }
                itemName.setOnClickListener(listener)
                itemName.setText(data.name)
                itemAddr.setText(data.address)
                itemPhone.setText(data.phone)
            }
        }

    }


    inner class ItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.good_item, parent, false)) {
        val itemName = itemView.item_name
        val itemAddr = itemView.item_address
        val itemPhone = itemView.item_phone
    }
}