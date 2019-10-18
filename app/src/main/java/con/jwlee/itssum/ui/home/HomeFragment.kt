package con.jwlee.itssum.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import con.jwlee.itssum.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import con.jwlee.itssum.data.CompareAdapter


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(con.jwlee.itssum.R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        setData(root)

        return root
    }

    fun setData(root : View) {
        val recyclerView : RecyclerView = root.findViewById(R.id.marketTable)

        val linearLayoutManager = LinearLayoutManager(this.requireContext())
        recyclerView.setLayoutManager(linearLayoutManager)

        var adapter = CompareAdapter()
        recyclerView.setAdapter(adapter)
    }
}