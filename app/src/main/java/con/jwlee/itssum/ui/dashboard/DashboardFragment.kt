package con.jwlee.itssum.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import con.jwlee.itssum.MainActivity
import con.jwlee.itssum.R
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(this, Observer {
            textView.text = it
        })

        root.postView.setCallback { zoneCode, address, buildingName ->
            Toast.makeText(this.requireContext(),"zoneCode: %s, address: %s, buildingName: %s".format(zoneCode, address, buildingName),Toast.LENGTH_LONG)
        }
        root.postView.setPostUrl("https://windsekirun.github.io/DaumPostCodeView/")
        root.postView.startLoad()

        return root
    }

    companion object {
        private const val num = "num"
        @JvmStatic
        fun newInstance(Number: Int): DashboardFragment {
            return DashboardFragment().apply {
                arguments = Bundle().apply {
                    putInt(num, Number)
                }
            }
        }
    }
}