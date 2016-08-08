package com.bandou.music.utils;


import com.bandou.music.model.AudioInfo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ClassName: AudiosSortUtils
 * Description: 音乐文件排序工具类
 * Creator: chenwei
 * Date: 16/8/8 下午3:30
 * Version: 1.0
 */
public class AudiosSortUtils {
    /**
     * Sort by order.
     * 按顺序排列
     *
     * @param mSourceAudios the m source audios
     */
    public static void sortByOrder(List<AudioInfo> mSourceAudios)
    {
        Collections.sort(mSourceAudios, new Comparator<AudioInfo>() {
            @Override
            public int compare(AudioInfo left, AudioInfo right) {
                int diff = -1;
                diff = left.getSinger().compareToIgnoreCase(right.getSinger());
                if (diff == 0) {
                    diff = left.getAlbum().compareToIgnoreCase(right.getAlbum());
                }
                if (diff == 0) {
                    diff = left.getName().compareToIgnoreCase(right.getName());
                }
                return diff;
            }
        });
    }


    /**
     * Sort by random.
     * 随机排列
     *
     * @param mSourceAudios the m source audios
     */
    public static void sortByRandom(List<AudioInfo> mSourceAudios)
    {
        Collections.shuffle(mSourceAudios);
    }
}
