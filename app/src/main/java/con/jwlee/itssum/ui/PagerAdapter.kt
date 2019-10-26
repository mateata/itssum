package con.jwlee.itssum.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import con.jwlee.itssum.ui.dashboard.DashboardFragment
import con.jwlee.itssum.ui.home.HomeFragment
import con.jwlee.itssum.ui.notifications.NotificationsFragment

class PagerAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val items = ArrayList<Fragment>()

    init {
        items.add(HomeFragment.newInstance(1))
        items.add(DashboardFragment.newInstance(2))
        items.add(NotificationsFragment.newInstance(3))
    }

    override fun getItem(position: Int): Fragment {
        return items.get(position)
    }

    override fun getCount(): Int {
        return items.size
    }

}