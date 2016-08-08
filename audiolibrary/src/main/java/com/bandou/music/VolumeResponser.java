package com.bandou.music;

import android.content.Context;
import android.media.AudioManager;

/**
 * ClassName: VolumeResponser
 * Description: 音量响应器
 * Creator: chenwei
 * Date: 16/8/8 下午3:54
 * Version: 1.0
 */
public class VolumeResponser {
    /**
     * Gets max volume.
     * 获取最大音量
     *
     * @param context the context
     * @return the max volume
     */
    public int getMaxVolume(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * Gets current volume.
     * 获取当前音量值
     *
     * @param context the context
     * @return the current volume
     */
    public int getCurrentVolume(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * Sets volume.
     * 设置音量
     *
     * @param context the context
     * @param volume  the volume
     */
    public void setVolume(Context context,int volume) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (volume > max) {
            volume = max;
        }
        if (volume < 0) {
            volume = 0;
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
    }

    /**
     * Decrease.
     * 减小音量，调出系统音量控制
     *
     * @param context the context
     */
    public void decrease(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,
                AudioManager.FX_FOCUS_NAVIGATION_UP);
    }

    /**
     * Increase.
     * 增加音量，调出系统音量控制
     *
     * @param context the context
     */
    public void increase(Context context) {
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,
                AudioManager.FX_FOCUS_NAVIGATION_UP);
    }
}
