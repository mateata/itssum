package con.jwlee.itssum.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import con.jwlee.itssum.R
import con.jwlee.itssum.data.GoodData
import con.jwlee.itssum.ui.BaseFragment
import con.jwlee.itssum.util.Util
import kotlinx.android.synthetic.main.good_detail.*
import kotlinx.android.synthetic.main.main_toolbar.*

class GoodDetailFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.good_detail, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        val bundle = arguments?.getBundle("detailData")
        val goodData : GoodData = bundle?.getSerializable("goodData") as GoodData
        //영역 setText
        header_title.setText(getString(R.string.title_dashboard))
        place_sector.setText(goodData.sector)
        place_name.setText(goodData.name)
        place_phone.setText(goodData.phone)
        place_intro.setText(goodData.intro)
        if("".equals(goodData.intro)) {
            place_intro.visibility = View.GONE
        }
        place_address.setText(goodData.address)
        place_time.setText(goodData.time)

        // 주차여부 따라서 체크와 색상 변경
        if(goodData.parking == true) {
            place_park_yn.setImageResource(R.drawable.check_icon_yes)
            place_park_text.setText(getString(R.string.good_detail_park_y))
            place_park_text.setTextColor(this.requireContext().getColor(R.color.MainColor))
        } else {
            place_park_yn.setImageResource(R.drawable.check_icon_no)
            place_park_text.setText(getString(R.string.good_detail_park_n))
            place_park_text.setTextColor(this.requireContext().getColor(R.color.TextColor))
        }
        // 배달여부로 체크와 색상 변경
        if(goodData.delivery == true) {
            place_delivery_yn.setImageResource(R.drawable.check_icon_yes)
            place_delivery_text.setText(getString(R.string.good_detail_delivery_y))
            place_delivery_text.setTextColor(this.requireContext().getColor(R.color.MainColor))
        } else {
            place_delivery_yn.setImageResource(R.drawable.check_icon_no)
            place_delivery_text.setText(getString(R.string.good_detail_delivery_n))
            place_delivery_text.setTextColor(this.requireContext().getColor(R.color.TextColor))
        }

        // 하단 품목/가격뷰. 항목이 없으면 지우고 있으면 값을 세팅
        if(goodData.itemName1 == "" || goodData.itemName1 == "-") {
            market_itemview1.visibility = View.GONE
        } else {
            market_itemname1.setText(goodData.itemName1)
            market_itemval1.setText(Util().commaNumber(goodData.itemval1))
        }
        if(goodData.itemName2 == "" || goodData.itemName2 == "-") {
            market_itemview2.visibility = View.GONE
        } else {
            market_itemname2.setText(goodData.itemName2)
            market_itemval2.setText(Util().commaNumber(goodData.itemval2))
        }
        if(goodData.itemName3 == "" || goodData.itemName3 == "-") {
            market_itemview3.visibility = View.GONE
        } else {
            market_itemname3.setText(goodData.itemName3)
            market_itemval3.setText(Util().commaNumber(goodData.itemval3))
        }
        bt_back.setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
        }

    }

    override fun onBack() {
        findNavController().navigate(R.id.navigation_dashboard)
    }
}