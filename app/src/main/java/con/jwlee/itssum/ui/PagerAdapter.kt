package con.jwlee.itssum.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import con.jwlee.itssum.ui.community.CommunityFragment
import con.jwlee.itssum.ui.home.HomeFragment
import con.jwlee.itssum.ui.menu.MenuFragment

class PagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val items = ArrayList<Fragment>()

    init {
        items.add(HomeFragment.newInstance(1))
        items.add(CommunityFragment.newInstance(2))
        items.add(MenuFragment.newInstance(3))
    }

    override fun getItem(position: Int): Fragment {
        return items.get(position)
    }

    override fun getCount(): Int {
        return items.size
    }

}