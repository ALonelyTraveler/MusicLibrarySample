package com.bandou.music.model;

import android.net.Uri;

import java.io.Serializable;

/**
 * ClassName: AudioInfo
 * Description: 本地音频信息
 * Creator: chenwei
 * Date: 16/8/8 下午3:31
 * Version: 1.0
 */
public class AudioInfo implements Serializable {
    /**
     * 无效的专辑或歌曲索引
     */
    public static final long INVALID_ID_INDEX = -1;
    /**
     * 数据库中唯一编号
     */
    private int id;
    /**
     * 歌曲名称
     */
    private String name;
    /**
     * 歌曲歌手
     */
    private String singer;
    /**
     * 专辑名称
     */
    private String album;
    /**
     * 音乐文件大小
     */
    private long size;
    /**
     * 专辑编号
     */
    private long albumId = INVALID_ID_INDEX;
    /**
     * 歌曲编号
     */
    private long songId = INVALID_ID_INDEX;
    /**
     * 音乐时长
     */
    private long duration;
    /**
     * 专辑封面图
     */
    private Uri albumArtUri;
    /**
     * 音乐文件路径
     */
    private Uri fileUri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getSongId() {
        return songId;
    }

    public void setSongId(long songId) {
        this.songId = songId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Uri getAlbumArtUri() {
        return albumArtUri;
    }

    public void setAlbumArtUri(Uri albumArtUri) {
        this.albumArtUri = albumArtUri;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    @Override
    public String toString() {
        return "AudioInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", album='" + album + '\'' +
                ", size=" + size +
                ", albumId=" + albumId +
                ", songId=" + songId +
                ", duration=" + duration +
                ", albumArtUri=" + albumArtUri +
                ", fileUri=" + fileUri +
                '}';
    }
}
