package com.isea.plugin.normal;

import java.util.List;

import com.isea.basic.BaseDownload;
import com.isea.basic.StopException;

public class NormalDownLoad extends BaseDownload{
	@Override
	public void downloadMore(final String savePath, 
			final String url, 
			final String selector,
			final String page,
			final int start,
			final int pageSize,
			final int timeout,
			final boolean first){
		isstart = true;
		//超时时间
		setTimeout(timeout);
		
		String childUrl = null;
		String pageTemp = page;
		if(pageTemp==null||pageTemp.equals("")){
			pageTemp = "";
		}
		else {
			if(!pageTemp.startsWith("/")){
				pageTemp = "/"+pageTemp;
			}
			if(!pageTemp.endsWith("/")){
				pageTemp += "/";
			}
		}
		int _start = start;
		int max = start + pageSize;
		int count = 1;
		List<String> list = null;
		
		for(;_start<max;_start++,count++){
			if(isstart){
				tips("正在下载 "+count+"/"+pageSize);
				if(_start==1&&first){
					childUrl = url + pageTemp;
				}
				else{
					childUrl = url + pageTemp + _start;
				}
				try {
					log("开始下载地址:"+childUrl);
					list = getSrcPath(childUrl, selector);
					downLoadImages(list, savePath);
				} catch (StopException e) {
					//停止下载
					log(e.getMessage());
					break;
				} catch (Exception e) {
					log(e.getMessage());
				}
			} else {
				log("终止下载!");
			}
		}
		if(isstart){
			tips("下载完成");
			progress(100);
		}
	}
}
