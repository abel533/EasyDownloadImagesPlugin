package com.isea.plugin.tieba;

import java.util.List;

import com.isea.basic.BaseDownload;
import com.isea.basic.IDownload;

public class TiebaDownLoad extends BaseDownload implements IDownload{
	private boolean isstart = true;
	
	/**
	 * 批量下载图片
	 * @param savePath
	 * @param url
	 * @param selector
	 * @param page
	 * @param pageSize
	 * @param first true - 首页不同
	 */
	public void downloadMore(final String savePath, 
			final String url, 
			final String selector,
			final String page,
			final int start,
			final int pageSize,
			final boolean first){
		String childUrl = null;
		String pageTemp = page;
		if(pageTemp.startsWith("/")){
			pageTemp = pageTemp.substring(1);
		}
		if(pageTemp.endsWith("/")){
			pageTemp = pageTemp.substring(pageTemp.length()-1);
		}
		pageTemp = "?"+pageTemp+"=";
		
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
				
			}else{
				log("终止下载!");
				break;
			}
		}
	}

	@Override
	public void stop() {
		isstart = false;
		progress(0);
	}
}
