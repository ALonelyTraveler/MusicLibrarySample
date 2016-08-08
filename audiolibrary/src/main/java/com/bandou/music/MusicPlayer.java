package com.bandou.music;

import com.bandou.music.controller.AbstractControllerResponser;
import com.bandou.music.controller.ControllerCallBack;
import com.bandou.music.controller.ControllerResponser;
import com.bandou.music.controller.IControllerResponser;
import com.bandou.music.model.AudioInfo;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: MusicPlayer
 * Description: 音乐播放器, 可假想成是一个MP3
 * Creator: chenwei
 * Date: 16/8/8 下午4:29
 * Version: 1.0
 */
public class MusicPlayer implements IControllerResponser, ControllerCallBack {
    private static MusicPlayer mPlayer = null;

    /**
     * Gets instance.
     * 获取单例
     *
     * @return the instance
     */
    public static MusicPlayer getInstance() {
        if (mPlayer == null) {
            mPlayer = new MusicPlayer();
        }
        return mPlayer;
    }

    /**
     * Instantiates a new Music player.
     */
    public MusicPlayer() {
        mController = new ControllerResponser();
        mProvider = new AudioProvider();
    }

    private AbstractControllerResponser mController;
    private AudioProvider mProvider;
    private boolean isEnabled;
    private ControllerCallBack mCallback;
    private Subscription timer = null;
    private int retainCount = 0;
    private boolean isPauseTimer = false;

    /**
     * Gets provider.
     *
     * @return the provider
     */
    public AudioProvider getProvider() {
        return mProvider;
    }

    /**
     * 启动播放器
     *
     * @param callBack the call back
     * @return boolean
     */
    public boolean startup(ControllerCallBack callBack) {
        if (isEnabled) {
            return true;
        }
        if (mController == null || mProvider == null) {
            return false;
        }
        mController.setCallback(this);
        isEnabled = true;
        this.mCallback = callBack;
        enable();
        return true;
    }

    /**
     * 关闭播放器
     */
    public void shutdown() {
        if (!isEnabled) {
            return;
        }
        if (mController != null) {
            mController.stop();
            mController.setCallback(null);
        }
        if (mProvider != null) {
            mProvider.release();
        }
        disabled();
        this.mCallback = null;
        isEnabled = false;
    }

    /**
     * 是否启动
     *
     * @return boolean
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    /**
     * 播放下一首
     */
    public void playNext() {
        if (mProvider != null && mController != null) {
            if (!mProvider.isEmpty()) {
                AudioInfo info = mProvider.getNextAudio();
                if (info != null && info.getFileUri() != null) {
                    mController.play(info.getFileUri().getPath());
                    mProvider.resetIndex();
                }
            }
        }
    }

    /**
     * 自动播放下一首
     */
    public void playNextAuto() {
        if (mProvider != null && mController != null) {
            if (!mProvider.isEmpty()) {
                AudioInfo info = mProvider.getNextAudio();
                if (info != null && info.getFileUri() != null) {
                    mController.play(info.getFileUri().getPath());
                }
            }
        }
    }

    /**
     * 播放上一首
     */
    public void playPrevious() {
        if (mProvider != null && mController != null) {
            if (!mProvider.isEmpty()) {
                AudioInfo info = mProvider.getPrevious();
                if (info != null && info.getFileUri() != null) {
                    mController.play(info.getFileUri().getPath());
                    mProvider.resetIndex();
                }
            }
        }
    }

    /**
     * 注册进度通知,只有在有监听的时候才进行循环读取
     */
    public void registerTimer() {
        retainCount++;
        if (retainCount > 0 && timer == null) {
            timer = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            progress(mController.getProgress());
                        }
                    });
        }
    }

    /**
     * 反注册进度通知
     */
    public void unregisterTimer() {
        if (retainCount <= 0) {
            return;
        }
        retainCount--;
        if (retainCount == 0 && timer != null) {
            timer.unsubscribe();
            timer = null;
        }
    }

    /**
     * 暂停进度通知,一般在触摸进度条时调用
     */
    public void pauseTimer() {
        if (timer != null) {
            timer.unsubscribe();
            timer = null;
            isPauseTimer = true;
        }
    }

    /**
     * 恢复进度通知,一般在触摸结束时调用
     */
    public void resumeTimer() {
        if (isPauseTimer) {
            timer = Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            progress(mController.getProgress());
                        }
                    });
        }
    }

    /**
     * ===============================================
     * start ----&gt;实现{@link IControllerResponser}的方法
     * ===============================================
     */
    @Override
    public int play(String path) {
        return mController != null ? mController.play(path) : ControllerResponser.ERROR_DATA;
    }

    @Override
    public void pause() {
        if (mController != null) {
            mController.pause();
        }
    }

    @Override
    public void resume() {
        if (mController != null) {
            mController.resume();
        }

    }

    @Override
    public void stop() {
        if (mController != null) {
            mController.stop();
        }
    }

    @Override
    public void seekTo(int progress) {
        if (mController != null) {
            mController.seekTo(progress);
        }
    }

    @Override
    public boolean isPlay() {
        return mController != null ? mController.isPlay() : false;
    }

    @Override
    public boolean isPrepared() {
        return mController != null ? mController.isPrepared() : false;
    }

    @Override
    public int getProgress() {
        return mController != null ? mController.getProgress() : 0;
    }

    /**
     * ===============================================
     *  end &gt;----实现{@link IControllerResponser}的方法
     * ===============================================
     */

    /**
     * ===============================================
     * start ----&gt;实现{@link ControllerCallBack}的方法
     * ===============================================
     */
    @Override
    public void enable() {
        if (mCallback != null) {
            mCallback.enable();
        }
    }

    @Override
    public void disabled() {
        if (mCallback != null) {
            mCallback.disabled();
        }
    }

    @Override
    public void startPlay(int progress) {
        if (mCallback != null) {
            mCallback.startPlay(progress);
        }
    }

    @Override
    public void pause(int progress) {
        if (mCallback != null) {
            mCallback.pause(progress);
        }
    }

    @Override
    public void error(int what, int extra) {
        if (mCallback != null) {
            mCallback.error(what, extra);
        }
    }

    @Override
    public void complete() {
        if (mProvider.isLastElement() || mProvider.isEmpty()) {
            if (mCallback != null) {
                mCallback.complete();
            }
        } else {
            AudioInfo info = mProvider.getNextAudio();
            if (info != null) {
                if (info != null && info.getFileUri() != null) {
                    mController.play(info.getFileUri().getPath());
                }
            }
        }
    }


    @Override
    public void progress(int progress) {
        if (mCallback != null) {
            mCallback.progress(progress);
        }
    }

    /**
     * ===============================================
     *  end ----&gt;实现{@link ControllerCallBack}的方法
     * ===============================================
     */
}
