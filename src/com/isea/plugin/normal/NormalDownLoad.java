package com.isea.plugin.normal;

import java.util.List;

import com.isea.basic.BaseDownload;
import com.isea.basic.IDownload;

public class NormalDownLoad extends BaseDownload implements IDownload{
	private boolean isstart = true;
	
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
			if(isstart){
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
				} catch (Exception e) {
					log(e.getMessage());
				}
			}
			else{
				log("终止下载!");
			}
		}
	}

	@Override
	public void stop() {
		isstart = false;
	}
}
