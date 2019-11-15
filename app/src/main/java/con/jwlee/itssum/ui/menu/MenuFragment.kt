package con.jwlee.itssum.ui.menu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.navigation.fragment.findNavController
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.ui.BaseFragment
import con.jwlee.itssum.ui.splash.SetLocalActivity
import con.jwlee.itssum.util.Util
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.home_toolbar.header_title
import kotlinx.android.synthetic.main.main_toolbar.*


class MenuFragment : BaseFragment(), AdapterView.OnItemClickListener, View.OnClickListener {


    var array_Menu: ArrayList<MenuItem> = ArrayList<MenuItem>()
    lateinit var menuAdapter : MenuAdapter
    lateinit var mContext : Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root : View = inflater.inflate(R.layout.fragment_menu, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = this.requireContext()
        addMenus()
        AppControl.appIdx = 3
        menuAdapter = MenuAdapter(this.requireContext(),R.layout.menu_item, array_Menu)
        itssum_menu.setOnItemClickListener(this)
        itssum_menu.adapter = menuAdapter
        (itssum_menu.adapter as MenuAdapter).notifyDataSetChanged()

        bt_back.visibility = View.GONE

        header_title.setText(R.string.title_menu)
        // 남은 버튼에 리스너 등록
        menubtn_calc.setOnClickListener(this)
        menubtn_favorite.setOnClickListener(this)
        menubtn_market.setOnClickListener(this)
        ieum_view.setOnClickListener(this)
        call_view.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.ieum_view -> { // 하단 이음어플 바로가기
                if(Util().getPackageList(mContext)) {
                    val intent : Intent =
                        mContext.getPackageManager().getLaunchIntentForPackage("gov.incheon.incheonercard")!!;
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent)
                } else {
                    val url = "market://details?id=" + "gov.incheon.incheonercard";
                    val i : Intent = Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(i);
                }
            }
            R.id.call_view -> {
                findNavController().navigate(R.id.navigation_calc)
            }
            else -> {Util().toastLong(mContext, getString(R.string.menu_prepare))}
        }

    }

    // 리스트에 있는 아이템 클릭
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position) {
            0 -> { //지역재설정으로 이
                val intent = Intent(mContext, SetLocalActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            }
            1 -> { Util().toastLong(mContext, getString(R.string.menu_prepare)) } // 공지사항~설정까지는 준비중 처리
            2 -> { Util().toastLong(mContext, getString(R.string.menu_prepare)) }
            3 -> { Util().toastLong(mContext, getString(R.string.menu_prepare)) }
            4 -> { Util().toastLong(mContext, getString(R.string.menu_prepare)) }
            else -> {Util().toastLong(mContext, getString(R.string.menu_prepare))}
        }
    }


    private fun addMenus() {
        val menu01 = MenuItem(getString(R.string.menu_location),true)
        val menu02 = MenuItem(getString(R.string.menu_notice),true)
        val menu03 = MenuItem(getString(R.string.menu_qna),true)
        val menu04 = MenuItem(getString(R.string.menu_help),true)
        val menu05 = MenuItem(getString(R.string.menu_setting),true)

        array_Menu.add(0, menu01)
        array_Menu.add(1, menu02)
        array_Menu.add(2, menu03)
        array_Menu.add(3, menu04)
        array_Menu.add(4, menu05)
    }


}