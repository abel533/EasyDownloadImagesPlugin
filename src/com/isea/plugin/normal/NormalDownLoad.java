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
			final boolean first){
		//开始下载
		isstart = true;
		String childUrl = null;
		String pageTemp = page;
		if(!pageTemp.startsWith("/")){
			pageTemp = "/"+pageTemp;
		}
		if(!pageTemp.endsWith("/")){
			pageTemp += "/";
		}
		int _start = start;
		List<String> list = null;
		
		for(;_start<pageSize;_start++){
			progress((int)(100*(float)_start-start)/pageSize);
			tips("正在下载第 "+_start+" 页");
			if(_start==1&&first){
				childUrl = url;
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
		}	
	}
}
