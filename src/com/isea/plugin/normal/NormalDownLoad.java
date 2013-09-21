package com.isea.plugin.normal;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.isea.basic.ALoggerProgress;
import com.isea.basic.IDownload;
import com.isea.basic.Md5Utils;

public class NormalDownLoad extends ALoggerProgress implements IDownload{
	private boolean isstart = true;
	
	/**
	 * 获取图片下载地址
	 * @param url
	 * @param selector
	 * @return
	 * @throws Exception
	 */
	public List<String> getSrcPath(String url,String selector) throws Exception{
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.select(selector);
		Element element = null;
		Iterator<Element> iter = elements.iterator();
		
		String src = null;
		List<String> srclist = new ArrayList<String>();
		while(iter.hasNext()){
			element = iter.next();
			src = element.attr("src");
			if(!src.startsWith("http")){
				src = url+"/"+src;
			}
			srclist.add(src);
		}
		return srclist;
	}
	
	public void downLoadImages(List<String> srcList,String savePath) throws Exception{
		if(srcList!=null&&srcList.size()>0){
			URL url = null;
			InputStream is = null;
			FileOutputStream fos = null;
			String fileName = null;
			String filePath = null;
			for(String src:srcList){
				try {
					url = new URL(src);
					fileName = Md5Utils.getMd5(src) + getFileType(src);
					filePath = savePath + fileName;
					is = url.openStream();
					fos = new FileOutputStream(filePath);
					byte[] bytes = new byte[2048];
					int length = 0;
					while((length=is.read(bytes))>0){
						fos.write(bytes, 0, length);
					}
					log("fileName : "+fileName+" - 下载成功!");
				} catch (Exception e) {
					log(e.getMessage());
				} finally {
					if(is!=null){
						is.close();
					}
					if(fos!=null){
						fos.close();
					}
				}
			}
		}
		else{
			log("资源地址为空，无可下载内容！");
		}
	}
	
	public String getFileType(String src){
		String srcTemp = src.toLowerCase();
		if(srcTemp.endsWith("jpg")){
			return ".jpg";
		}
		else if(srcTemp.endsWith(".jpeg")){
			return ".jpeg";
		}
		else if(srcTemp.endsWith(".png")){
			return ".png";
		}
		else if(srcTemp.endsWith(".gif")){
			return ".gif";
		}
		return ".others";
	}
	
	/**
	 * 下载
	 * @param savePath
	 * @param url
	 * @param selector
	 */
	public void download(String savePath, String url, String selector){
		List<String> list = null;
		try {
			list = getSrcPath(url, selector);
			downLoadImages(list, savePath);
		} catch (Exception e) {
			log(e.getMessage());
		}
	}
	
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
