package com.bandou.music.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.bandou.music.model.AudioInfo;
import com.bandou.music.model.SongArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ClassName: MusicLoader
 * Description: 音乐加载器
 * Creator: chenwei
 * Date: 16/8/8 下午3:30
 * Version: 1.0
 * &lt;uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/&gt;
 */
public class MusicLoader {

    private final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");

    private static class MusicLoaderInstance {
        private static MusicLoader loader = new MusicLoader();
    }

    private MusicLoader() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MusicLoader getInstance() {
        return MusicLoaderInstance.loader;
    }

    /**
     * 加载音乐
     *
     * @param context
     * @return
     */
    private SongArray loadMusic(Context context, long myAlbumId, long mySongId) {
        String[] projection = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media._ID,
        };
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Audio.Media.IS_MUSIC + "=1" + (myAlbumId == AudioInfo.INVALID_ID_INDEX ? "" : (" and " + MediaStore.Audio.Media.ALBUM_ID + "=" + myAlbumId)),
                null,
                "LOWER(" + MediaStore.Audio.Media.ARTIST + ") ASC, " +
                        "LOWER(" + MediaStore.Audio.Media.ALBUM + ") ASC, " +
                        "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC"
        );
        if (cursor == null) {
            List<AudioInfo> emptyList = Collections.emptyList();
            return new SongArray(emptyList,SongArray.INVALID_INDEX);
        }
        List<AudioInfo> items = new ArrayList<AudioInfo>();
        int index = SongArray.INVALID_INDEX;
        boolean isFind = false;
        try {
            if (cursor.moveToFirst()) {
                int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int size = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
                int displayName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                do {
                    AudioInfo item = new AudioInfo();
                    item.setName(cursor.getString(title));
                    item.setAlbum(cursor.getString(album));
                    item.setAlbumId(cursor.getLong(albumId));
                    item.setSinger(cursor.getString(artist));
                    item.setDuration(cursor.getLong(duration));
                    item.setAlbumArtUri(ContentUris.withAppendedId(albumArtUri, cursor.getLong(albumId)));
                    item.setFileUri(Uri.parse(cursor.getString(data)));
                    item.setSize(cursor.getLong(size));
                    item.setSongId(cursor.getLong(id));
                    items.add(item);
                    if (!isFind) {
                        index++;
                        if (item.getSongId() == mySongId) {
                            isFind = true;
                        }
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return new SongArray(items,isFind?index:SongArray.INVALID_INDEX);
    }

    /**
     * 加载所有的音乐
     *
     * @param context the context
     * @return list
     */
    public List<AudioInfo> loadAllMusic(Context context) {
        return loadMusic(context, AudioInfo.INVALID_ID_INDEX,SongArray.INVALID_INDEX).getSongs();
    }

    /**
     * Load all music song array.
     *
     * @param context the context
     * @param songId  the song id
     * @return the song array
     */
    public SongArray loadAllMusic(Context context, long songId) {
        return loadMusic(context, AudioInfo.INVALID_ID_INDEX,songId);
    }

    /**
     * 加载指定专辑下的音乐
     *
     * @param context the context
     * @param albumId the album id
     * @return list
     */
    public List<AudioInfo> loadMusicByAlbumId(Context context, long albumId) {
        return loadMusic(context, albumId,SongArray.INVALID_INDEX).getSongs();
    }

    /**
     * Load music by album id song array.
     *
     * @param context the context
     * @param albumId the album id
     * @param songId  the song id
     * @return the song array
     */
    public SongArray loadMusicByAlbumId(Context context, long albumId, long songId) {
        return loadMusic(context, albumId, songId);
    }

    /**
     * 加载专辑列表
     *
     * @param context the context
     * @return list
     */
    public List<AudioInfo> loadAlbum(Context context) {
        String[] projection = new String[]{
                " DISTINCT " + MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.SIZE,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media._ID,
        };

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Audio.Media.IS_MUSIC + "=1) GROUP BY (" + MediaStore.Audio.Media.ALBUM_ID,
                null,
                "LOWER(" + MediaStore.Audio.Media.ARTIST + ") ASC, " +
                        "LOWER(" + MediaStore.Audio.Media.ALBUM + ") ASC, " +
                        "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC"
        );
        if (cursor == null) {
            return Collections.emptyList();
        }
        List<AudioInfo> items = new ArrayList<AudioInfo>();
        try {
            if (cursor.moveToFirst()) {
                int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                int albumId = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
                int data = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
                int size = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
                int displayName = cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME);
                int id = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
                do {
                    AudioInfo item = new AudioInfo();
                    item.setName(cursor.getString(title));
                    item.setAlbum(cursor.getString(album));
                    item.setAlbumId(cursor.getLong(albumId));
                    item.setSinger(cursor.getString(artist));
                    item.setDuration(cursor.getLong(duration));
                    item.setAlbumArtUri(ContentUris.withAppendedId(albumArtUri, cursor.getLong(albumId)));
                    item.setFileUri(Uri.parse(cursor.getString(data)));
                    item.setSize(cursor.getLong(size));
                    item.setSongId(cursor.getLong(id));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }
        return items;
    }


}
