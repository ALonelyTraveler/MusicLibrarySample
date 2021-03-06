package com.bandou.music.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @ClassName: BaseActivity
 * @Description: say something
 * @author: chenwei
 * @version: V1.0
 * @Date: 16/7/26 下午4:21
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    @BindView(R.id.toolbar)
    public Toolbar tbTitle;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        mContext = this;
        initWindow();
        int layoutId = getLayoutId();
        if (layoutId > 0) {
            View contentView = LayoutInflater.from(mContext).inflate(layoutId, null, false);
            setContentView(contentView);
            /**采用注入方式初始化控件变量**/
            ButterKnife.bind(this, contentView);
            initView();
        }
        tbTitle.setTitle("音乐播放");
        setSupportActionBar(tbTitle);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * 初始化控件
     */
    public abstract void initView();

    /**
     * 初始化Window属性
     */
    public void initWindow() {

    }

    /**
     * 获取布局文件的id
     *
     * @return
     */
    public abstract int getLayoutId();
}
