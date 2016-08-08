package com.bandou.music.controller;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

/**
 * ClassName: ControllerResponser
 * Description:控制响应器
 * Creator: chenwei
 * Date: 16/8/8 下午4:40
 * Version: 1.0
 */
public class ControllerResponser extends AbstractControllerResponser {
    /**
     * 音乐播放实例
     */
    protected MediaPlayer mPlayer = null;

    /**
     * ERROR:错误
     * IDLE:空闲
     * PREPARING:正在准备中
     * PLAYING:正在播放中
     * PAUSE:暂停中
     * COMPLETE:完成
     */
    public static final int ERROR = -1;
    /**
     * The constant IDLE.
     */
    public static final int IDLE = 0;
    /**
     * The constant PREPARING.
     */
    public static final int PREPARING = 1;
    /**
     * The constant PLAYING.
     */
    public static final int PLAYING = 2;
    /**
     * The constant PAUSE.
     */
    public static final int PAUSE = 3;
    /**
     * The constant COMPLETE.
     */
    public static final int COMPLETE = 4;


    /**
     * {@link #play(String)}返回值的取值
     * NORMAL:未出现错误
     * ERROR_DATA:数据错误（路径不正确或AudioInfo对象为空)
     * ERROR_LOADING:加载时出现异常
     */
    public static final int NORMAL = 0;
    /**
     * The constant ERROR_DATA.
     */
    public static final int ERROR_DATA = 1;
    /**
     * The constant ERROR_LOADING.
     */
    public static final int ERROR_LOADING = 2;

    /**
     * 当前状态
     */
    private int status = IDLE;

    /**
     * 播放指定路径的音乐
     * @param path 音乐文件路径
     * @return 播放状态
     */
    @Override
    public int play(String path) {
        if (TextUtils.isEmpty(path)) {
            return ERROR_DATA;
        }
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        }
        try {
            mPlayer.reset();
            status = PREPARING;
            mPlayer.setDataSource(path);
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnErrorListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnSeekCompleteListener(this);
            mPlayer.prepareAsync();
        } catch (IOException e) {
            Log.i("MusicController", "Exception from initPlay");
            e.printStackTrace();
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }
            status = ERROR;
            return ERROR_LOADING;
        }
        return NORMAL;
    }

    /**
     * 暂停播放
     */
    @Override
    public void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            status = PAUSE;
            if (mCallback != null) {
                mCallback.pause(getProgress());
            }
        }
    }

    /**
     * 恢复播放
     */
    @Override
    public void resume() {
        if (mPlayer != null && isPrepared()) {
            if (status != PAUSE & status != COMPLETE) {
                return;
            }
            mPlayer.start();
            status = PLAYING;
            if (mCallback != null) {
                mCallback.startPlay(getProgress());
            }
        }
    }

    /**
     * 停止播放
     */
    @Override
    public void stop() {
        if (status == IDLE) {
            return;
        }
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            status = IDLE;
            if (mCallback != null) {
                mCallback.stop();
            }
        }
    }

    @Override
    public void seekTo(int progress) {
        if (mPlayer != null && isPrepared()) {
            mPlayer.seekTo(progress);
        }
    }

    /**
     * 当前是否正在播放
     * @return boolean
     */
    @Override
    public boolean isPlay() {
        return status == PLAYING;
    }

    /**
     * 是否已经准备就绪
     * @return boolean
     */
    @Override
    public boolean isPrepared() {
        return mPlayer!=null&&status > PREPARING;
    }

    @Override
    public int getProgress() {
        return isPrepared() ? mPlayer.getCurrentPosition() : 0;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (mPlayer != null) {
            mPlayer.pause();
            mPlayer.seekTo(0);
        }
        status = COMPLETE;
        if (mCallback != null) {
            mCallback.complete();
        }
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        status = ERROR;
        if (mCallback != null) {
            mCallback.error(what,extra);
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        status = PLAYING;
        if (mCallback != null) {
            mCallback.startPlay(getProgress());
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }
}
