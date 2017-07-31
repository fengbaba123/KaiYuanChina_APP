package com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseFragment;
import com.jiyun.txl.kaiyuanchina_app.Base.BaseListAdapter;
import com.jiyun.txl.kaiyuanchina_app.Base.ViewHolder;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.FirstField;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.jiyun.txl.kaiyuanchina_app.HomeFragment.kaiyuanruanjian.FengLeiFragment.fragmentManager;

/**
 * 开源软件分类 一级
 */

public class SecondLeiFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private List<FirstField.SoftwareTypeBean> list;
    private MyAdapter myadapter;
    @Bind(R.id.listview)
    ListView listview;
    private List<String> list1 = new ArrayList<>();
    private List<FirstField.SoftwareTypeBean> softwareTypes1;
    private SharedPreferences sharedPreferences;
private SharedPreferences.Editor editor;
    @Override
    protected int getlayout() {
        return R.layout.listview;
    }

    @Override
    protected void initView(View view) {
        list = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    protected void initData() {

    }

    private List<FirstField.SoftwareTypeBean> softwareTypes;

    @Override
    protected void listener() {

        Map<String, String> map = new HashMap<>();
        map.put("tag", sharedPreferences.getString("tag", "273"));
        HttpFactory.getFactory().Get(Utils.KYRJ_FIELD, map, new MyCallback() {
            @Override
            public void successful(String body) {
                //二级页面
                Log.e("风雷", body + "");
                XStream xStream = new XStream();
                xStream.alias("oschina", FirstField.class);
                xStream.alias("softwareType", FirstField.SoftwareTypeBean.class);
                FirstField firstField = (FirstField) xStream.fromXML(body);
                list.addAll(firstField.getSoftwareTypes());
                softwareTypes1 = firstField.getSoftwareTypes();
                myadapter = new MyAdapter(App.baseActivity
                        , list);
                listview.setAdapter(myadapter);
            }

            @Override
            public void failure(String errorMessage) {
                Toast.makeText(App.baseActivity, "异常", Toast.LENGTH_SHORT).show();
            }
        });
        listview.setOnItemClickListener(this);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        editor.putString("searchTag",list.get(position).getTag());
        editor.commit();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fenlei_frame, new ThirdLeiFragment(), "三级");
        fragmentTransaction.addToBackStack("三级返回");
        fragmentTransaction.commit();
        Toast.makeText(getActivity().getApplicationContext(), "哈哈哈", Toast.LENGTH_SHORT).show();
    }

    //
    class MyAdapter extends BaseListAdapter<FirstField.SoftwareTypeBean> {

        public MyAdapter(Context context, List<FirstField.SoftwareTypeBean> list) {
            super(context, list, R.layout.item_fenlei);
        }

        @Override
        protected void convert(ViewHolder holder, FirstField.SoftwareTypeBean s) {
            holder.settext(R.id.fenlei_text, s.getName());
        }
    }
}
