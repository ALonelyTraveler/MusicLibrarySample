# MusicLibrarySample
提供简单的API调用，快速实现本地播放音乐器的功能。

##当前版本(VERSION)
[![Maven Central](https://img.shields.io/badge/VERSION-0.1.0-orange.svg)](https://bintray.com/gcssloop/maven/sutil/view)

## gradle依赖

	compile 'com.bandou:music:VERSION'
	
## 项目依赖

	compile 'org.greenrobot:eventbus:3.0.0'
	compile 'io.reactivex:rxjava:1.1.8'
	compile 'io.reactivex:rxandroid:1.2.1'

## 使用

	//开启音乐播放器,并指定状态回调的方式
	MusicPlayer.getInstance().startup(new EventBusCallback());
	//注册音乐播放进度通知(如果MusicPlayer中没有注册者则不进行Timer读取进度),一般在onCreate或onResume中调用
	MusicPlayer.getInstance().registerTimer();
	//设置播放列表
	MusicPlayer.getInstance().getProvider().setAudios(ArrayList<AudioInfo>);
	//设置音乐播放的模式(随机、顺序、循环等参考源码com.bandou.music.mode.PlayMode)
	MusicPlayer.getInstance().getProvider().updatePlayMode(Int);
	//播放指定路径的音乐
	MusicPlayer.getInstance().play(String);
	//音乐播放器是否准备就绪
	if (MusicPlayer.getInstance().isPrepared()) {
		//音乐播放器是否正在播放
		if (MusicPlayer.getInstance().isPlay()) {
			//暂停
			MusicPlayer.getInstance().pause();
		} else {
			//恢复播放
			MusicPlayer.getInstance().resume();
		}
	}
	//播放上一首
	MusicPlayer.getInstance().playPrevious();
	//播放下一首
	MusicPlayer.getInstance().playNext();
	//反注册播放进度通知,一般在onPause或onDestroy中调用
	MusicPlayer.getInstance().unregisterTimer();
	//关闭音乐播放器,并释放资源
	MusicPlayer.getInstance().shutdown();
	
##其它

	//获取专辑
	MusicLoader.getInstance().loadAlbum(Context);
	//获取指定专辑下的音乐列表
	MusicLoader.getInstance().loadMusicByAlbumId(mContext, albumId);
	//获取所有音乐列表
	MusicLoader.getInstance().loadAllMusic(mContext);
	//减少音量
	VolumeResponser#decrease(Context);
	//增加音量
	VolumeResponser#increase(Context);
	//按歌手名称、专辑名称、音乐名称顺序排列
	AudioSortUtils.sortByOrder(ArrayList<AudioInfo>);
	//对ArrayList进行随机洗牌
	AudioSortUtils.sortByRandom(ArrayList<AudioInfo>);
	
## 更新日志

>
>0.1.0 (2016-8-9)
>
>* 初始化版本
>
