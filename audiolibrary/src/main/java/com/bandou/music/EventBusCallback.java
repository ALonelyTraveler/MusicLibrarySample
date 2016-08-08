package com.bandou.music;

import com.bandou.music.controller.ControllerCallBack;
import com.bandou.music.event.MediaEnableEvent;
import com.bandou.music.event.MediaEvent;
import org.greenrobot.eventbus.EventBus;

/**
 * ClassName: EventBusCallback
 * Description: EventBus方式的回调处理
 * Creator: chenwei
 * Date: 16/8/8 下午4:39
 * Version: 1.0
 */
public class EventBusCallback implements ControllerCallBack {
    @Override
    public void enable() {
        EventBus.getDefault().post(new MediaEnableEvent(true));
    }

    @Override
    public void disabled() {
        EventBus.getDefault().post(new MediaEnableEvent(false));
    }

    @Override
    public void startPlay(int progress) {
        EventBus.getDefault().post(new MediaEvent(MediaEvent.ACTION_PLAY, progress));
    }

    @Override
    public void pause(int progress) {
        EventBus.getDefault().post(new MediaEvent(MediaEvent.ACTION_PAUSE, progress));
    }

    @Override
    public void error(int what, int extra) {
        EventBus.getDefault().post(new MediaEvent(MediaEvent.ACTION_ERROR, MediaEvent.NO_SUPPORT_PROGRESS));
    }

    @Override
    public void complete() {
        EventBus.getDefault().post(new MediaEvent(MediaEvent.ACTION_ALL_COMPLETE,0));
    }

    @Override
    public void stop() {
        EventBus.getDefault().post(new MediaEvent(MediaEvent.ACTION_STOP,0));
    }

    @Override
    public void progress(int progress) {
        EventBus.getDefault().post(new MediaEvent(MediaEvent.ACTION_PROGRESS,progress));
    }
}
