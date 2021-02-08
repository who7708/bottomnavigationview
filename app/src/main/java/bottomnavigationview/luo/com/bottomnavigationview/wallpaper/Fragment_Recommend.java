package bottomnavigationview.luo.com.bottomnavigationview.wallpaper;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bottomnavigationview.luo.com.bottomnavigationview.PhotoShowActivity;
import bottomnavigationview.luo.com.bottomnavigationview.R;
import bottomnavigationview.luo.com.bottomnavigationview.adapter.RecommendAdapter;
import bottomnavigationview.luo.com.bottomnavigationview.bean.BaseResponse;
import bottomnavigationview.luo.com.bottomnavigationview.http.GetRequestInterface;
import bottomnavigationview.luo.com.bottomnavigationview.http.NetWorkManager;
import bottomnavigationview.luo.com.bottomnavigationview.utils.Tools;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2019/7/2.
 */

public class Fragment_Recommend extends Fragment {

    private static final String TAG = "Fragment_Recommend";

    private View myView;
    private LinearLayout mNetworkNo;
    private TextView mRetry;
    private RecyclerView mRecommendRv;

    private GridLayoutManager gridLayoutManager;
    private LayoutAnimationController controller;
    private RecommendAdapter recommendAdapter;
    private List<String> mListUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.wallpaper_recommend, container, false);
        initData();
        initView();
        return myView;
    }

    private void initData() {
        mListUrl = new ArrayList<>();
        recommendAdapter = new RecommendAdapter(myView.getContext(), mListUrl);
        gridLayoutManager = new GridLayoutManager(myView.getContext(), 2);
        Request();
    }

    private void Request() {
        NetWorkManager.getInstance().init();
        // 步骤5:创建 网络请求接口 的实例
        GetRequestInterface request = NetWorkManager.getRequest();
        Call<BaseResponse> call = request.getCall(Tools.WallPaperAndroid,
                Tools.GetAppsByCategory,
                Tools.Daily_Selection,
                0,
                99);
        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(@NonNull Call<BaseResponse> call, @NonNull Response<BaseResponse> response) {
                Log.d(TAG, "onResponse: " + response.body());
                BaseResponse baseResponse = response.body();
                if (baseResponse == null) {
                    return;
                }
                for (BaseResponse.DataBean data : baseResponse.getData()) {
                    Log.d(TAG, "data.getUrl() = " + data.getUrl());
                    Log.d(TAG, "data.getC_t() = " + data.getC_t());
                    Log.d(TAG, "data.getPid() = " + data.getPid());
                    Log.d(TAG, "data.getFav_total() = " + data.getFav_total());
                    mListUrl.add(data.getUrl());
                }
                recommendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<BaseResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "retrofit  is error");
            }
        });
    }

    private void initView() {
        mNetworkNo = (LinearLayout) myView.findViewById(R.id.network_no);
        mRetry = (TextView) myView.findViewById(R.id.retry);
        mRecommendRv = (RecyclerView) myView.findViewById(R.id.recommend_rv);


        controller = new LayoutAnimationController(AnimationUtils.loadAnimation(getContext(), R.anim.item_anim));

        mRecommendRv.setLayoutAnimation(controller);
        mRecommendRv.setLayoutManager(gridLayoutManager);
        mRecommendRv.setAdapter(recommendAdapter);

        recommendAdapter.setOnItemClickListener(new RecommendAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Log.d("lcr", " listurl = " + mListUrl.get(position));
                Intent mIntent = new Intent(getContext(), PhotoShowActivity.class);
                mIntent.putExtra("PhotoViewUrl", mListUrl.get(position));
                getContext().startActivity(mIntent);
            }
        });
    }

}
