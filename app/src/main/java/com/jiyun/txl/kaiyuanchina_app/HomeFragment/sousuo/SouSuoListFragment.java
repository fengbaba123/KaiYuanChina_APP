package com.jiyun.txl.kaiyuanchina_app.HomeFragment.sousuo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.jiyun.txl.kaiyuanchina_app.Activity.SouSuoActivity;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseListAdapter;
import com.jiyun.txl.kaiyuanchina_app.Base.ViewHolder;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.DB.MyManager;
import com.jiyun.txl.kaiyuanchina_app.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * 用于展示历史搜索的Fragment
 */

public class SouSuoListFragment extends BaseFragment {
    private SharedPreferences sha;
    private SharedPreferences.Editor editor;
    private MyAdapter myAdapter;
    @Bind(R.id.sousuo_list)
    ListView sousuoList;
    @Bind(R.id.sousuo_clear)
    Button sousuoClear;
    private String string;
    private MyManager manager;
    private List<String> list;
    private FragmentManager manager1;
    private static String s;

    @Override
    protected int getlayout() {
        return R.layout.sousuo_fragment;
    }

    @Override
    protected void initView(View view) {

        sha = getActivity().getSharedPreferences("data", MODE_PRIVATE);

        //数据库Manager
        manager = new MyManager(getActivity().getApplicationContext());
        //FragmentManager
        manager1 = getActivity().getSupportFragmentManager();
        editor = sha.edit();
    }

    @Override
    protected void initData() {
        list = manager.queryAll();
        myAdapter = new MyAdapter(getActivity().getApplicationContext(), list);
        if (!list.isEmpty()) {
            sousuoList.setAdapter(myAdapter);
            sousuoClear.setVisibility(View.VISIBLE);
        } else {
            sousuoClear.setVisibility(View.GONE);
        }
    }

    @Override
    protected void listener() {
        sousuoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                s = list.get(position);
                editor.putString("sousuo", s);
                editor.commit();
                FragmentTransaction fragmentTransaction = manager1.beginTransaction();
                fragmentTransaction.replace(R.id.sousuo_fragme, new SouSuoJieGuoFragment());
                fragmentTransaction.commit();
                ((SouSuoActivity) App.baseActivity).setEd(s);
            }
        });
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
//这个是删除数据
    @OnClick(R.id.sousuo_clear)
    public void onViewClicked() {
        list.clear();
        manager.delete();
        initData();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void setParmars(Bundle bundle) {
//        super.setParmars(bundle);
        string = (String) bundle.get("sousuo");

    }

    class MyAdapter extends BaseListAdapter<String> {


        public MyAdapter(Context context, List<String> list) {
            super(context, list, R.layout.item_souusolist);
        }

        @Override
        protected void convert(ViewHolder holder, String s) {
            holder.settext(R.id.tv_wangqiansousuo, s);

        }
    }

}
