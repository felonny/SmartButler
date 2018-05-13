# SmartButler(生活管家)
生活管家,是一款实用工具App，集成了与智能机器人语音交流，微信精选文章推荐，趣味视频播放，个人中心，短信语言提醒等功能。
界面采用的是主流生活工具App，Tab切换界面。视频采用的是“开眼”App视频源。<br>
## 主要功能<br>
### *动态权限检测，为了兼容Android6.0+ 在登录app后进行授权询问，如果缺少权限可以去设置中重新授权<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/checkquan.png)<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/check.png)<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/settingadd.png)<br>
### 1.闪屏界面，引导视图<br>
在首次打开App时，通过闪屏界面进入引导视图，再进入主页登录界面。<br>
### 2.登录界面<br>
采用Bmob框架，实现登录，注册，记住密码，修改密码。<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/loginshow.jpg)<br>
### 3.服务管家<br>
通过图灵机器人接口实现，接入科大讯飞SDK，实现与机器人语音交流功能。<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/robotshow.jpg)<br>  
### 4.微信精选<br>
通过聚合数据中的微信精选接口实现，使用RxVolley框架实现http请求。<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/wechatlist.jpg)<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/wechatshow.jpg)<br>
### 5.趣味视频<br>
运用【开眼】App的每日精选作为数据源。采用Vitamio视频播放框架实现视频播放功能。<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/videolist.jpg)<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/videoshow.jpg)<br>
### 6.个人中心<br>
采用circleImageView框架实现圆形头像，Bmob框架实现修改个人信息功能，以及物流查询和归属地查询的功能。<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/usershow.jpg)<br>
### 7.设置<br>
设置语言播报的开关，智能短信提醒开关，新版本检测，版本更新功能。<br>
![](https://raw.githubusercontent.com/felonny/SmartButler/master/app/src/main/res/drawable/settingshow.jpg)<br>
### 技术要点
采用的第三方框架
1.Bugly,用于故障检查
2.Bmob,服务框架
3.circleImageView,圆形头像剪裁
4.RxVolley,网络传输
5.picasso,图片加载
6.NumberProgress,进度条

### 声明

【智能管家】是一款实用工具app，视频数据来源于开眼视频视频，数据接口均属于非正常渠道获取，请勿用于商业用途，原作公司拥有所有权利。
