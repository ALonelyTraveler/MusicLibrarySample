package com.bandou.music.model;

/**
 * ClassName: PlayMode
 * Description: 播放模式
 * Creator: chenwei
 * Date: 16/8/8 下午3:31
 * Version: 1.0
 */
public class PlayMode {
    //SINGLE LOOP ORDER RANDOM
    /**
     * 从高到低使用一个int值表示当前的播放模式
     * 第一位RANDOM：表示是否随机，为1时表示随机
     * 第二位ORDER: 表示是否顺序，为1时表示顺序播放
     * 第三位LOOP:表示是否循环播放
     * 第四位SINGLE:表示是否单曲播放
     * <pre>
     * SINGLE:单曲播放
     * LOOP:循环播放
     * ORDER:顺序播放
     * RANDOM:随机播放
     * SINGLE_LOOP:单曲循环播放
     * ORDER_LOOP:顺序循环播放
     * RANDOM_LOOP:随机循环播放
     * </pre>
     */

    public static final int DEFAULT = 0x00;

    public static final int SINGLE = 0x08;  //1<<3

    public static final int LOOP = 0x04;    //1<<2

    public static final int ORDER = 0x02;   //1<<1

    public static final int RANDOM = 0x01;  //1<<0

    public static final int SINGLE_LOOP = SINGLE | LOOP;

    public static final int ORDER_LOOP = ORDER | LOOP;

    public static final int RANDOM_LOOP = RANDOM | LOOP;
}
