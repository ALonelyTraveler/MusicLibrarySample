package com.bandou.music;

import com.bandou.music.model.AudioInfo;
import com.bandou.music.model.PlayMode;
import com.bandou.music.utils.AudiosSortUtils;

import java.util.List;

import static com.bandou.music.model.PlayMode.*;

/**
 * ClassName: AudioProvider
 * Description: 音乐提供器, 为控制器提供音乐
 * Creator: chenwei
 * Date: 16/8/8 下午4:29
 * Version: 1.0
 */
public class AudioProvider {
    /**
     * 音乐列表
     */
    private List<AudioInfo> mAudios;
    /**
     * 开始播放时的索引
     */
    private int startIndex;
    /**
     * 当前播放的索引
     */
    private int nowIndex;
    /**
     * 播放模式
     * {@link PlayMode#ORDER}
     * {@link PlayMode#ORDER_LOOP}
     * {@link PlayMode#RANDOM}
     * {@link PlayMode#RANDOM_LOOP}
     * {@link PlayMode#SINGLE}
     * {@link PlayMode#SINGLE_LOOP}
     */
    private int mPlayMode = ORDER;

    /**
     * 设置当前音乐列表
     *
     * @param audios     the audios
     * @param startIndex the start index
     */
    public void setAudios(List<AudioInfo> audios, int startIndex) {
        if (audios == null || audios.isEmpty() || startIndex < 0 || startIndex >= audios.size()) {
            throw new IllegalAccessError("传入的参数不正确!!");
        }
        this.mAudios = audios;
        this.startIndex = startIndex;
        this.nowIndex = startIndex;
    }

    /**
     * 获取当前音乐列表
     *
     * @return audios
     */
    public List<AudioInfo> getAudios() {
        return mAudios;
    }

    /**
     * 获取当前播放的音乐
     *
     * @return audio info
     */
    public AudioInfo get() {
        return isEmpty() || (nowIndex < 0 || nowIndex >= mAudios.size()) ? null : mAudios.get(nowIndex);
    }

    /**
     * 更新当前的播放模式并刷新播放列表
     *
     * @param playMode the play mode
     */
    public void updatePlayMode(int playMode) {
        if (playMode != mPlayMode) {
            if (isEmpty()) {
                return;
            }
            boolean nowOrder = (playMode & ORDER) == ORDER;
            boolean nowRandom = (playMode & RANDOM) == RANDOM;
            synchronized (mAudios) {
                AudioInfo lastAudio = mAudios.get(nowIndex);
                if (nowRandom) {  //洗牌为随机
                    AudiosSortUtils.sortByRandom(mAudios);
                } else {  //洗牌为顺序
                    AudiosSortUtils.sortByOrder(mAudios);
                }
                startIndex = nowIndex = (lastAudio != null ? mAudios.indexOf(lastAudio) : 0);
                mPlayMode = playMode;
            }
        }
    }

    /**
     * 获取当前的播放模式
     *
     * @return play mode
     */
    public int getPlayMode() {
        return mPlayMode;
    }

    /**
     * 获取下首音乐
     *
     * @return next audio
     */
    public AudioInfo getNextAudio() {
        if (isEmpty()) {
            return null;
        }
        nowIndex = (nowIndex + 1 >= mAudios.size()) ? 0 : (++nowIndex);
        return mAudios.get(nowIndex);
    }

    /**
     * 获取上首音乐
     *
     * @return previous
     */
    public AudioInfo getPrevious() {
        if (isEmpty()) {
            return null;
        }
        nowIndex = (nowIndex - 1 < 0 ? mAudios.size() - 1 : (--nowIndex));
        return mAudios.get(nowIndex);
    }

    /**
     * 是否为循环中的最后一个元素,以下情况会出现已经结束的情况,其它情况下{@link #isLastElement()}恒返回false
     * 1)顺序一次播放结束 {@link PlayMode#ORDER}
     * 2)单曲一次播放结束 {@link PlayMode#SINGLE}
     * 3)随机一次播放结束 {@link PlayMode#RANDOM}
     *
     * @return boolean
     */
    public boolean isLastElement() {
        if ((mPlayMode & ORDER) == ORDER || (mPlayMode & SINGLE) == SINGLE || (mPlayMode & RANDOM) == RANDOM) {
            if (isEmpty()) {
                return true;
            }
            if (mAudios.size() == 1) {
                return true;
            } else if (startIndex == 0 ? (nowIndex == mAudios.size() - 1) : (nowIndex == startIndex - 1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 音乐列表是否为空
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return mAudios == null || mAudios.isEmpty();
    }

    /**
     * 设置当前播放索引
     *
     * @param nowIndex the now index
     */
    public void setNowIndex(int nowIndex) {
        if (isEmpty()) {
            return;
        }
        if ((nowIndex < 0 || nowIndex >= mAudios.size())) {
            throw new IllegalAccessError("传入的参数不正确!!");
        }
        this.nowIndex = nowIndex;
    }

    /**
     * Gets now index.
     *
     * @return the now index
     */
    public int getNowIndex() {
        return nowIndex;
    }

    /**
     * 设置开始索引
     *
     * @param startIndex the start index
     */
    public void setStartIndex(int startIndex) {
        if (isEmpty()) {
            return;
        }
        if ((startIndex < 0 || startIndex >= mAudios.size())) {
            throw new IllegalAccessError("传入的参数不正确!!");
        }
        this.startIndex = startIndex;
    }

    /**
     * Gets start index.
     *
     * @return the start index
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * 重置索引,让开始播放的索引指向当前播放的索引
     * 手动点击下一曲或上一曲时最好重置一下索引
     */
    public void resetIndex() {
        if (!isEmpty()) {
            startIndex = nowIndex;
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mAudios != null) {
            mAudios.clear();
            mAudios = null;
        }
        startIndex = nowIndex = -1;
    }
}
