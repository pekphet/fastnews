package com.youzi.fastnews.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.youzi.fastnews.App;
import com.youzi.fastnews.R;
import com.youzi.fastnews.adapter.InviteAdapter;
import com.youzi.fastnews.global.WechatConstants;
import com.youzi.fastnews.utils.ImageTools;
import com.youzi.fastnews.utils.TextUtil;
import com.youzi.fastnews.utils.WechatUtils;
import com.youzi.fastnews.view.BasePopwindow;
import com.youzi.fastnews.view.MyListView;

import java.util.ArrayList;
import java.util.List;


/**
 * 每日邀请排行界面
 * <p>
 * Created by ywb on 2016/6/2.
 */
public class InviteFriendsActivity extends Activity implements View.OnClickListener {

    private ScrollView scrollView;

    private ImageView qrCode;

    private TextView inviteFriendsUrl;

    private Button copyUrl;

    private Button quickShare;

    private TextView master;

    private TextView copyQrcodeTv;

    private MyListView listView;

    private InviteAdapter adapter;

    private List<String> entities = new ArrayList<>();

    private String url;
    private Bitmap bitmap;
    private String path;
    private String masterID;
    private boolean isPaused;
    private ImageView in;
    private String shareUrl;
    private String shareTitle;
    private String shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_friends);
        initData();
        initView();
    }

    private void initData() {


    }

    public void initView() {

        in = (ImageView) findViewById(R.id.in_back);

        scrollView = (ScrollView) findViewById(R.id.invite_scrollview);

        qrCode = (ImageView) findViewById(R.id.invite_friends_qrcode);

        inviteFriendsUrl = (TextView) findViewById(R.id.invite_friends_url);

        copyUrl = (Button) findViewById(R.id.copy_url);

        quickShare = (Button) findViewById(R.id.quick_share);

        master = (TextView) findViewById(R.id.master);

        copyQrcodeTv = (TextView) findViewById(R.id.copy_invite_code_tv);

        listView = (MyListView) findViewById(R.id.invite_rule_listview);

        adapter = new InviteAdapter(this, entities);

        listView.setAdapter(adapter);

        if (!TextUtils.isEmpty(masterID)) {
            master.setText("我的邀请码：" + masterID);
        }

        qrCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                View view = v;
                BasePopwindow popwindow = new BasePopwindow(InviteFriendsActivity.this, R.layout.popwindow_layout_item);
                popwindow.setOnPopupwindowClickListener(new BasePopwindow.OnPopupwindowClickListener() {
                    @Override
                    public void click() {//点击保存二维码图片

                        if (bitmap != null) {
                            path = ImageTools.saveImageToGallery(InviteFriendsActivity.this, bitmap);
                            Toast.makeText(InviteFriendsActivity.this, "保存成功,路径在" + path, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                popwindow.showPopWindow(view);

                return true;
            }
        });

        scrollView.requestChildFocus(qrCode, scrollView);

        in.setOnClickListener(this);
        copyUrl.setOnClickListener(this);
        quickShare.setOnClickListener(this);
        copyQrcodeTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.in_back:

                finish();

                break;

            case R.id.copy_url:

                url = inviteFriendsUrl.getText().toString();

                TextUtil.copy(url, this);

                Toast.makeText(this, "复制成功:" + TextUtil.paste(this), Toast.LENGTH_SHORT).show();

                break;

            case R.id.quick_share:

                if (TextUtils.isEmpty(inviteFriendsUrl.getText().toString())) {
                    Toast.makeText(this, "分享失败:" + TextUtil.paste(this), Toast.LENGTH_SHORT).show();
                    return;
                }

                WechatUtils.wechatShare(InviteFriendsActivity.this, App.iWXAPI, WechatConstants.WXSceneTimeline, shareUrl, shareTitle, shareContent);

                break;

            case R.id.copy_invite_code_tv:

                TextUtil.copy(masterID, this);
                Toast.makeText(this, "复制邀请码成功:" + TextUtil.paste(this), Toast.LENGTH_SHORT).show();

                break;

        }


    }

//    @Override
//    public void asyncData(NewsAndShareFriendsUrlEntity shareUrlEntity) {
//
//        initInviteFriendsData(shareUrlEntity);
//
//    }
//
//    @Override
//    public void asyncFailed(String errorMsg) {
//
//    }
//
//    private IAsyncFresher<ResponseBaseEntity> mRouleteAddAsyncFresher = new IAsyncFresher<ResponseBaseEntity>() {
//
//        @Override
//        public void asyncData(ResponseBaseEntity entity) {
//
//            ZToast.r(InviteFriendsActivity.this, "每日分享奖励已到账");
//
//        }
//
//        @Override
//        public void asyncFailed(String errorMsg) {
//
//            ZToast.r(InviteFriendsActivity.this, "分享成功");
//
//        }
//    };

    @Override
    public void onResume() {
        super.onResume();
        if (isPaused) {
            if (scrollView != null && qrCode != null) {
                scrollView.requestChildFocus(qrCode, scrollView);
            }
        }
        isPaused = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
    }

//    public void initInviteFriendsData(NewsAndShareFriendsUrlEntity entity) {
//
//        inviteFriendsUrl.setText(entity.getShare_url() + "/" + NetDataCache.getLoginCache().getUid());
//
//        entities.clear();
//
//        entities.addAll(entity.getReward_rule());
//
//        adapter.notifyDataSetChanged();
//
//        try {
//            bitmap = QrCodeUtils.Create2DCode(inviteFriendsUrl.getText().toString(), getResources(), true, R.mipmap.ic_launcher);
//            qrCode.setImageBitmap(bitmap);
//        } catch (WriterException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//
//    }

}
