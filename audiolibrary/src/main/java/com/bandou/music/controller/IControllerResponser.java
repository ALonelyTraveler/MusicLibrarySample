package com.bandou.music.controller;

/**
 * ClassName: IControllerResponser
 * Description:  控制器接口
 * Creator: chenwei
 * Date: 16/8/8 下午3:20
 * Version: 1.0
 */
public interface IControllerResponser {
    /**
     * 播放指定路径的音乐
     *
     * @param path 音乐路径
     * @return 错误状态
     */
    int play(String path);

    /**
     * 暂停当前播放
     */
    void pause();

    /**
     * 恢复当前播放
     */
    void resume();

    /**
     * 停止当前播放
     */
    void stop();

    /**
     * 调节播放进度
     *
     * @param progress 设置进度
     */
    void seekTo(int progress);

    /**
     * 当前是否播放
     *
     * @return  boolean
     */
    boolean isPlay();

    /**
     * 是否准备完成
     *
     * @return boolean
     */
    boolean isPrepared();

    /**
     * 获取当前播放进度
     *
     * @return 当前进度
     */
    int getProgress();
}
