package bottomnavigationview.luo.com.bottomnavigationview.wallpaper;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bottomnavigationview.luo.com.bottomnavigationview.R;

/**
 * Created by Administrator on 2019/7/2.
 */

public class Fragment_Local extends Fragment {

    private View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.wallpaper_local, container, false);
        return myView;
    }
}
