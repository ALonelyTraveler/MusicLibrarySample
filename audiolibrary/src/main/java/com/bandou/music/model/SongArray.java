package com.bandou.music.model;

import java.util.List;

/**
 * ClassName: SongArray
 * Description: 带有指定songId索引的实体类
 * Creator: chenwei
 * Date: 16/8/8 下午3:31
 * Version: 1.0
 */
public class SongArray {

    /**
     * 无效索引
     */
    public static final int INVALID_INDEX = -1;

    /**
     * 歌曲列表
     */
    private List<AudioInfo> songs;
    /**
     * 指定songId在歌曲列表中的索引位置
     */
    private int songIndex = INVALID_INDEX;

    public SongArray(){

    }

    public SongArray(List<AudioInfo> songs, int songIndex) {
        this.songs = songs;
        this.songIndex = songIndex;
    }

    public List<AudioInfo> getSongs() {
        return songs;
    }

    public void setSongs(List<AudioInfo> songs) {
        this.songs = songs;
    }

    public int getSongIndex() {
        return songIndex;
    }

    public void setSongIndex(int songIndex) {
        this.songIndex = songIndex;
    }
}
