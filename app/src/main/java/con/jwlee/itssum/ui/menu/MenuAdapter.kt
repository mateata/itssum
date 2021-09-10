package con.jwlee.itssum.ui.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import con.jwlee.itssum.R
import kotlinx.android.synthetic.main.menu_item.view.*


class MenuAdapter(context: Context, resource: Int, settingMenuList: ArrayList<MenuItem>) :
    ArrayAdapter<MenuItem>(context, resource, settingMenuList) {

    var setMenuList: ArrayList<MenuItem> = settingMenuList
    val mResource = resource

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var v = LayoutInflater.from(parent.context).inflate(mResource, parent, false)


        val mMenu = setMenuList.get(position)
        if (mMenu != null) {
            v.tv_menu.text = mMenu.title
            v.iv_menu.setImageResource(getMenuIcon(position))
        }

        return v
    }

    private fun getMenuIcon(menuPosition: Int): Int {
        var resourceID = 0
        when (menuPosition) {
            0 -> resourceID = R.drawable.location_icon
            1 -> resourceID = R.drawable.notice_icon
            2 -> resourceID = R.drawable.qna_icon
            3 -> resourceID = R.drawable.call_icon
            4 -> resourceID = R.drawable.setting_icon
            else -> {
            }
        }

        return resourceID
    }

}