package com.bandou.music.sample;

import android.app.Application;
import android.content.Context;
import com.bandou.music.MusicPlayer;
import com.bandou.music.event.MediaEnableEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @ClassName: ApplicationContext
 * @Description: say something
 * @author: chenwei
 * @version: V1.0
 * @Date: 16/7/27 下午5:32
 */
public class ApplicationContext extends Application {
    public static Context mContext;
    private DefaultMusicNotification musicNotification;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        configMusicController();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMediaEnableEvent(MediaEnableEvent event) {
        if (event.enabled) {
            if (musicNotification == null) {
                musicNotification = new DefaultMusicNotification(mContext, R.mipmap.ic_launcher);
            }
            musicNotification.showNotification(MusicPlayer.getInstance().getProvider().get());
        }
        else{
            musicNotification.hideNotification();
        }
    }

    public void configMusicController() {
    }
}
