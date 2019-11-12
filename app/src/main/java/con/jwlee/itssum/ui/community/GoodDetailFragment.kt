package con.jwlee.itssum.ui.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import con.jwlee.itssum.R
import con.jwlee.itssum.data.GoodData
import con.jwlee.itssum.util.DLog
import kotlinx.android.synthetic.main.good_detail.view.*

class GoodDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.good_detail, container, false)
        init(root)
        return root
    }

    fun init(view : View) {
        val bundle = arguments?.getBundle("detailData")
        val goodData : GoodData = bundle?.getSerializable("goodData") as GoodData
        DLog().e(goodData.address)
        view.text_temp.setText(goodData.address)
    }
}