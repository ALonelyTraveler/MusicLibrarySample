package com.bandou.music.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import com.bandou.music.MusicPlayer;

/**
 * @ClassName: MusicPlayerReceiver
 * @Description: 监听蓝牙设备的MediaButton和a2dp断开
 * @author: chenwei
 * @version: V1.0
 * @Date: 16/6/15 下午4:54
 */
public class MusicPlayerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_MEDIA_BUTTON)) {
            if (intent.getExtras() == null) {
                return;
            }
            KeyEvent keyEvent = (KeyEvent) intent.getExtras().get(Intent.EXTRA_KEY_EVENT);
            if (keyEvent == null) {
                return;
            }
            if (keyEvent.getAction() != KeyEvent.ACTION_DOWN)
                return;

            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_HEADSETHOOK:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                    if (MusicPlayer.getInstance().isPlay()) {
                        MusicPlayer.getInstance().pause();
                    } else {
                        if (MusicPlayer.getInstance().isPrepared()){
                            MusicPlayer.getInstance().resume();
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                    if (MusicPlayer.getInstance().isPrepared()){
                        MusicPlayer.getInstance().resume();
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                    if (MusicPlayer.getInstance().isPlay()){
                        MusicPlayer.getInstance().pause();
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    if (MusicPlayer.getInstance().isPrepared()){
                        MusicPlayer.getInstance().stop();
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    if (MusicPlayer.getInstance().isPlay()){
                        MusicPlayer.getInstance().playNext();
                    }
                    break;
                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    if (MusicPlayer.getInstance().isPlay()){
                        MusicPlayer.getInstance().playPrevious();
                    }
                    break;
            }
        }
    }
}
