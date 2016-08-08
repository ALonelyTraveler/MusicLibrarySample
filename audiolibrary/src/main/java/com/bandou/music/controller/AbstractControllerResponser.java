package com.bandou.music.controller;

import android.media.MediaPlayer;

/**
 * ClassName: AbstractControllerResponser
 * Description: 抽象控制器类
 * Creator: chenwei
 * Date: 16/8/8 下午3:55
 * Version: 1.0
 */
public abstract class AbstractControllerResponser implements MediaPlayer.OnCompletionListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener, IControllerResponser {

    protected ControllerCallBack mCallback;

    public void setCallback(ControllerCallBack callback)
    {
        this.mCallback = callback;
    }


    public ControllerCallBack getCallback()
    {
        return mCallback;
    }

}
