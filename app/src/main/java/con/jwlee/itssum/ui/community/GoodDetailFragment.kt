package con.jwlee.itssum.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import con.jwlee.itssum.R
import con.jwlee.itssum.data.GoodData
import con.jwlee.itssum.ui.BaseFragment
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

        bt_back.setOnClickListener {
            findNavController().navigate(R.id.navigation_dashboard)
        }
    }

    override fun onBack() {
        findNavController().navigate(R.id.navigation_dashboard)
    }
}