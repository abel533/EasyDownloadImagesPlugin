package com.isea.plugin.cnbig;

import com.isea.basic.BaseDownload;
import com.isea.basic.StopException;

import java.util.List;

public class CnbigDownLoad extends BaseDownload{
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
			final int timeout,
			final boolean first){
		isstart = true;
		//超时时间
		setTimeout(timeout);
		
		String childUrl = null;
		String tempUrl = url;

		int _start = start;
		int max = start + pageSize;
		int count = 1;
		List<String> list = null;
		
		for(;_start<max;_start++,count++){
			if(isstart){
				tips("正在下载 "+count+"/"+pageSize);
				if(_start==1&&first){
					childUrl = url + page;
				}
				else{
					childUrl = tempUrl + "_" + _start + page;
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
