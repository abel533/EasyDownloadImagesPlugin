EasyDownloadImagesPlugin
========================

EasyDownloadImages插件开发

主程序地址：https://github.com/abel533/EasyDownloadImages

欢迎各位开发插件

只要fork该插件项目，模仿原有的两个插件即可。

快速开发

 * 复制包normal到当前位置，修改normal名字（例如tieba）。
 * 修改后，将包内的两个类名进行相应的修改。
 * 右键当前包名，export > jar即可
 
插件使用
 * 将插件的jar包放到plugin下，添加plugin.ini配置，程序启动即可看到
 * 每个插件可以拥有一个对应的plugin.ini，如:tieba.ini，添加插件时，将ini和jar放入到plugin下即可。

已有插件
 * 一般模式 - 针对page/,page/2,page/3这样的网址
 * 贴吧模式 - 针对url?page=2,url?page=3这样的网址
 * 4chan - 针对一般模式的网址，但是获取的是href的资源，不是src的
 * 后缀模式 - 针对page/2.html,page/3.htm这样的网址