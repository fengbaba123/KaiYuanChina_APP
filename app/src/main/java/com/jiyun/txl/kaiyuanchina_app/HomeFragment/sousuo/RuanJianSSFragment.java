package com.jiyun.txl.kaiyuanchina_app.HomeFragment.sousuo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.QueryBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;

/**
 * 搜索结果软件
 */

public class RuanJianSSFragment extends BaseFragment {
    @Bind(R.id.kaiyuanzixun_pullView)
    PullToRefreshRecyclerView kaiyuanzixunPullView;
    private SharedPreferences sharedPreferences;

    @Override
    protected int getlayout() {
        return R.layout.kaiyuanzixun;
    }

    @Override
    protected void initView(View view) {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
        kaiyuanzixunPullView.setLayoutManager(manager);
        //标识一样 让她用一个sharedPreferencess
        sharedPreferences = getActivity().getSharedPreferences("data", MODE_PRIVATE);
        //请求网络
        Map<String, String> map = new HashMap<>();
        map.put("catalog", "software");
        map.put("content", sharedPreferences.getString("sousuo", ""));
        Log.e("kankan1",sharedPreferences.getString("sousuo", "")+"ha");
        map.put("pageIndex", "0");
        map.put("pageSize", "20");
        HttpFactory.getFactory().Get(Utils.SOUSUO, map, new MyCallback() {
            @Override
            public void successful(String body) {
                //解析
                XStream xStream = new XStream();
                xStream.alias("oschina", QueryBean.class);
                xStream.alias("result", QueryBean.ResultBean.class);
                QueryBean queryBean = (QueryBean) xStream.fromXML(body);
                List<QueryBean.ResultBean> results = queryBean.getResults();
                kaiyuanzixunPullView.setAdapter(new MypullAdapter(
                        getActivity().getApplicationContext(), results
                ));
            }

            @Override
            public void failure(String errorMessage) {

            }
        });

    }

    @Override
    protected void initData() {

    }


    @Override
    protected void listener() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            Log.e("运行","SS");
        }
    }

    class MypullAdapter extends BaseAdapter<QueryBean.ResultBean> {

        public MypullAdapter(Context context, List<QueryBean.ResultBean> datas) {
            super(context, R.layout.item_tuijian, datas);
        }

        @Override
        public void convert(ViewHolder holder, QueryBean.ResultBean resultBean) {
            holder.setText(R.id.tv_title,resultBean.getTitle());
            holder.setText(R.id.tv_body,resultBean.getDescription());

        }
    }

    @Override
    protected void onShow() {
        super.onShow();
        Toast.makeText(getActivity().getApplicationContext(), "显示了", Toast.LENGTH_SHORT).show();
    }
}
