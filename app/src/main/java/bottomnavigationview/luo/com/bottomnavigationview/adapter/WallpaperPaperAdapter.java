package bottomnavigationview.luo.com.bottomnavigationview.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Chris on 2019/7/3.
 */

public class WallpaperPaperAdapter extends FragmentPagerAdapter{

    private List<Fragment> mList;

    public WallpaperPaperAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }
}
