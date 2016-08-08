package com.bandou.music.event;


/**
 * ClassName: MediaEvent
 * Description: say something
 * Creator: chenwei
 * Date: 16/8/8 下午4:40
 * Version: 1.0
 */
public class MediaEvent {
    /**
     * 不提供当前进度
     */
    public static final int NO_SUPPORT_PROGRESS = -1;

    public static final int ACTION_PLAY = 1;
    public static final int ACTION_PAUSE = 2;
    public static final int ACTION_ERROR = 3;
    public static final int ACTION_PROGRESS = 4;
    public static final int ACTION_ALL_COMPLETE = 5;
    public static final int ACTION_STOP = 6;

    private final int action;
    private final int progress;

    public MediaEvent(int action, int progress) {
        this.action = action;
        this.progress = progress;
    }

    public int getAction() {
        return action;
    }

    public int getProgress() {
        return progress;
    }
}
