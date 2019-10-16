package con.jwlee.itssum.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import con.jwlee.itssum.R
import kotlinx.android.synthetic.main.fragment_home.*
import con.jwlee.itssum.data.MarketParser

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        val tbutton : Button = root.findViewById(R.id.tbutton)

        tbutton.setOnClickListener() {
            val marParser = MarketParser()
            marParser.mParser(marParser.bigXml, this.requireContext())
            textView.text = "DDDD"
        }


        return root
    }
}