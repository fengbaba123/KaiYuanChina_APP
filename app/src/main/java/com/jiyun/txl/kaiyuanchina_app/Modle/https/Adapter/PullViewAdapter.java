package com.jiyun.txl.kaiyuanchina_app.Modle.https.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.androidkun.adapter.BaseAdapter;
import com.androidkun.adapter.ViewHolder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.jiyun.txl.kaiyuanchina_app.Activity.DongTanXiangQingActivity;
import com.jiyun.txl.kaiyuanchina_app.Activity.DrawActivity;
import com.jiyun.txl.kaiyuanchina_app.App;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.CallBack.MyCallback;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.DianZanBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.bean.TanYiTanBean;
import com.jiyun.txl.kaiyuanchina_app.Modle.https.httpss.HttpFactory;
import com.jiyun.txl.kaiyuanchina_app.R;
import com.jiyun.txl.kaiyuanchina_app.Utils.Dates;
import com.jiyun.txl.kaiyuanchina_app.Utils.Keys;
import com.jiyun.txl.kaiyuanchina_app.Utils.Utils;
import com.thoughtworks.xstream.XStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个是动弹的适配器
 */

public class PullViewAdapter extends BaseAdapter<TanYiTanBean.TweetBean> {
    private String SystemDate;
    private ImageView imageView;
    private SharedPreferences sha;
    private String islike;
    private String date;
    private ImageView zanImageView;
    public PullViewAdapter(Context context, List<TanYiTanBean.TweetBean> datas) {
        super(context, R.layout.item_zuixindongtan, datas);
        sha = context.getSharedPreferences("data", Context.MODE_PRIVATE);
    }

    @Override
    public void convert(ViewHolder holder, TanYiTanBean.TweetBean tweetBean) {



        //这个是监听的方法
        listener(holder, tweetBean);
        //内容
        holder.setText(R.id.item_newsdongtan_author_body, tweetBean.getBody());
        //这个是发表人的名字
        holder.setText(R.id.item_newsdongtan_author_name, tweetBean.getAuthor());
        InputStream inputStream = new ByteArrayInputStream(tweetBean.getPortrait().getBytes());
        //找id
        zanImageView = holder.getView(R.id.item_newsdongtan_author_zanImage);
        //如果等于1 代表点赞过 出绿色图片 否则白色图片
        if ("1".equals(tweetBean.getIsLike())) {
            zanImageView.setImageResource(R.drawable.ic_thumbup_actived);
        } else {
            zanImageView.setImageResource(R.drawable.ic_thumb_normal);
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        //头像id
        final ImageView imageView = holder.getView(R.id.item_newsdongtan_author_head);
        //设置发表的图片id
        ImageView image = holder.getView(R.id.item_newsdongtan_author_ImageView);
        Glide.with(App.baseActivity).load(tweetBean.getImgBig()).into(image);
        Glide.with(App.baseActivity).load(tweetBean.getPortrait())
                .asBitmap()
                .centerCrop()
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imageView.setImageDrawable(circularBitmapDrawable);
                    }
                });
        //如果赞等于0 就给赞 不给0
        if ("0".equals(tweetBean.getLikeCount())) {
            holder.setText(R.id.item_newsdongtan_author_zan, "赞");
        }else{

        holder.setText(R.id.item_newsdongtan_author_zan, tweetBean.getLikeCount());
        }
        //分辨不同的手机
        holder.setText(R.id.item_newsdongtan_author_pinlun, tweetBean.getCommentCount());
        switch (tweetBean.getAppclient()) {
            case "3":
                holder.setText(R.id.item_newsdongtan_author_phone, "Android");
                break;
            case "4":
                holder.setText(R.id.item_newsdongtan_author_phone, "iphone");
                break;
            case "1":
                holder.setText(R.id.item_newsdongtan_author_phone, "   ");
                break;
        }
        //转化时间
        date = Dates.getDate(tweetBean.getPubDate());
        holder.setText(R.id.item_newsdongtan_author_date, date);


//
    }

    /**
     *   这个是监听的方法
     * @param holder holder
     * @param tweetBean 实体类
     */
    private void listener(final ViewHolder holder, final TanYiTanBean.TweetBean tweetBean) {
        //一个数组boolean 用来判断当前页面是否点赞过         不能写成成员
        final boolean[] boo = {true};
        //这个是点击赞的监听
        holder.setOnclickListener(R.id.item_newsdongtan_author_zanImage, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //每次点击的时候给islike赋值
                islike = tweetBean.getIsLike();
                //判断是否登陆
                if (sha.getString("uid", "").isEmpty()) {
                    Toast.makeText(context, "请登录", Toast.LENGTH_SHORT).show();
                } else {
                    //判断是否已经赞过  赞过再次点击就取消赞
                    if ("1".equals(islike)) {
//                        Toast.makeText(context, "你已经点赞过了", Toast.LENGTH_SHORT).show();
                        quXiaoZan(tweetBean, holder, boo);
                    } else {
                        //判断boolean类型 一开始为true
                        if(boo[0]){
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("tweetid", tweetBean.getId());
                            map.put("uid", sha.getString("uid", ""));
                            map.put("ownerOfTweet", tweetBean.getAuthorid());
                            HttpFactory.getFactory().Get(Utils.DONGTAN_ZAN, map, new MyCallback() {
                                @Override
                                public void successful(String body) {
//                                    Log.e("zan", body);
                                    XStream xStream = new XStream();
                                    xStream.alias("oschina", DianZanBean.class);
                                    DianZanBean dianZanBean = (DianZanBean) xStream.fromXML(body);
                                    //Toast 点赞状态
                                    Toast.makeText(context, dianZanBean.getResult().getErrorMessage(), Toast.LENGTH_SHORT).show();
//                                    TextView text = holder.getView(R.id.item_newsdongtan_author_zan);
                                    //  获取imae的id
                                    zanImageView = holder.getView(R.id.item_newsdongtan_author_zanImage);
                                    //点赞成功给把小手变成绿色
                                    zanImageView.setImageResource(R.drawable.ic_thumbup_actived);
                                    //本地增加赞的数量
                                    int i = Integer.parseInt(tweetBean.getLikeCount()) + 1;
                                    //同时islike为1
                                    islike = "1";
                                    //改变赞的数量
                                    holder.setText(R.id.item_newsdongtan_author_zan, String.valueOf(i));
                                    //这个刷新不要写
//                                    notifyDataSetChanged();
                                }

                                @Override
                                public void failure(String errorMessage) {

                                }
                            });
                            boo[0] = false;
                        }else{
//                            Toast.makeText(App.baseActivity, "你已经赞过了", Toast.LENGTH_SHORT).show();
                            //取消赞
                            quXiaoZan(tweetBean, holder, boo);
                        }

                    }
                }
            }
        });
        //这个是点击动弹item的跳转页面
        holder.setOnclickListener(R.id.dongtan_lin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DongTanXiangQingActivity.class);
                List<TanYiTanBean.TweetBean.UserBean> likeList = tweetBean.getLikeList();
                intent.putParcelableArrayListExtra(Keys.DONGTAN_ZAN_LIST, (ArrayList<? extends Parcelable>) likeList);
                intent.putExtra(Keys.DONGTAN_HEAD, tweetBean.getPortrait());
                intent.putExtra(Keys.DONGTAN_NAME, tweetBean.getAuthor());
                intent.putExtra(Keys.DONGTAN_BODY, tweetBean.getBody());
                intent.putExtra(Keys.DONGTAN_DATE, date);
                intent.putExtra(Keys.DONGTAN_ZAN_SIZE, tweetBean.getLikeCount());
                intent.putExtra(Keys.DONGTAN_ID,tweetBean.getId());
                intent.putExtra(Keys.DONGTAN_PINGLUN_SIZE,tweetBean.getCommentCount());
                App.baseActivity.startActivity(intent);

            }
        });
        //点击发表的图像的时候 变成大图
        holder.setOnclickListener(R.id.item_newsdongtan_author_ImageView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(App.baseActivity, DrawActivity.class);
                intent.putExtra("drawbe", tweetBean.getImgBig());
                App.baseActivity.startActivity(intent);

            }
        });
        //点击品论的时候弹出popWindow
        holder.setOnclickListener(R.id.item_newsdongtan_author_pinlun, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop(tweetBean);
            }
        });
    }
//取消赞的方法
    private void quXiaoZan(final TanYiTanBean.TweetBean tweetBean, final ViewHolder holder, final boolean[] boo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("tweetid", tweetBean.getId());
        map.put("uid", sha.getString("uid", ""));
        map.put("ownerOfTweet", tweetBean.getAuthorid());
        HttpFactory.getFactory().Get(Utils.QUXIAO_ZAN, map, new MyCallback() {
            @Override
            public void successful(String body) {
                zanImageView = holder.getView(R.id.item_newsdongtan_author_zanImage);
                zanImageView.setImageResource(R.drawable.ic_thumb_normal);
                boo[0] = true;
                holder.setText(R.id.item_newsdongtan_author_zan, tweetBean.getLikeCount());
            }

            @Override
            public void failure(String errorMessage) {

            }
        });
    }

    /**
     * 这是用来展示popWindow的
     */
    private void pop(final TanYiTanBean.TweetBean tweetBean) {
        View view = LayoutInflater.from(App.baseActivity).inflate(R.layout.popwindow_pinglun,null);
        final EditText editText = (EditText) view.findViewById(R.id.pinglun_ed);
        Button button = (Button) view.findViewById(R.id.pinlun_Btn);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT
                ,ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(TextUtils.isEmpty(editText.getText())){
                   Toast.makeText(App.baseActivity, "请输入内容", Toast.LENGTH_SHORT).show();
               }else{
                   Map<String,String> map = new HashMap<String, String>();
                   map.put("catalog","3");
                   map.put("id",tweetBean.getId());
                   map.put("uid",sha.getString("uid", ""));
                   map.put("content",editText.getText().toString().trim());
                   map.put("isPostToMyZone","0");
               HttpFactory.getFactory().Post(Utils.FABIAO_PINGLUN, map, new MyCallback() {
                   @Override
                   public void successful(String body) {
//                       Log.e("kankan",body);
                       Toast.makeText(App.baseActivity, "发表成功", Toast.LENGTH_SHORT).show();
                       popupWindow.dismiss();
                   }

                   @Override
                   public void failure(String errorMessage) {

                   }
               });
               }
            }
        });
        View view1 = LayoutInflater.from(App.baseActivity).inflate(R.layout.zuixinrimenidongtan,null);
        popupWindow.showAtLocation(new View(App.baseActivity), Gravity.BOTTOM,0,0);

    }

}
