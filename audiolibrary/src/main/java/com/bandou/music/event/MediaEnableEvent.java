package com.bandou.music.event;

/**
 * ClassName: MediaEnableEvent
 * Description: 音乐播放器启动与关闭通知
 * Creator: chenwei
 * Date: 16/8/8 下午4:46
 * Version: 1.0
 */
public class MediaEnableEvent {
    public final boolean enabled;

    public MediaEnableEvent(boolean enabled) {
        this.enabled = enabled;
    }
}
