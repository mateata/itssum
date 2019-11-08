package con.jwlee.itssum.ui

import androidx.fragment.app.Fragment
import con.jwlee.itssum.MainActivity


open class BaseFragment : Fragment(), MainActivity.OnBackPressedListener  {



    override fun onBack() {

    }


}