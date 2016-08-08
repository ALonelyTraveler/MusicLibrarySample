package com.bandou.music.controller;

/**
 * ClassName: ControllerCallBack
 * Description: 控制回调,子类用于广播通知
 * Creator: chenwei
 * Date: 16/8/8 下午4:00
 * Version: 1.0
 */
public interface ControllerCallBack {

    /**
     * 启动
     */
    public void enable();

    /**
     * 关闭
     */
    public void disabled();

    /**
     * 开始播放
     * @param progress  当前进度
     */
    public void startPlay(int progress);

    /**
     * 暂停播放
     *
     * @param progress 当前进度
     */
    public void pause(int progress);

    /**
     * 错误
     *
     * @param what  错误what
     * @param extra  错误extra
     */
    public void error(int what, int extra);

    /**
     * 播放完成
     */
    public void complete();

    /**
     * 停止
     */
    public void stop();

    /**
     * 当前进度
     *
     * @param progress 当前进度
     */
    public void progress(int progress);
}
