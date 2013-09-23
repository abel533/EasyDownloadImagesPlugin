package com.isea.basic;

import java.io.File;
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

public abstract class BaseDownload extends ALoggerProgress implements IDownload{
	protected boolean isstart = true;
	private int timeout = 3000;
	protected void setTimeout(int timeout){
		this.timeout = timeout;
	}
	/**
	 * 获取图片下载地址
	 * @param url
	 * @param selector 
	 * @return
	 * @throws Exception
	 */
	public List<String> getSrcPath(String url,String selector) throws Exception{
		Document doc = Jsoup.connect(url).timeout(timeout).get();
		Elements elements = doc.select(selector);
		Element element = null;
		Iterator<Element> iter = elements.iterator();
		
		String src = null;
		List<String> srclist = new ArrayList<String>();
		while(iter.hasNext()){
			element = iter.next();
			src = element.attr("src");
			if(src.startsWith("//")){
				src = "http:"+src;
			}
			else if(!src.startsWith("http")){
				src = url+"/"+src;
			}
			srclist.add(src);
		}
		return srclist;
	}
	
	/**
	 * 下载
	 * @param srcList
	 * @param savePath
	 * @throws Exception
	 */
	public void downLoadImages(List<String> srcList,String savePath) throws Exception{
		if(srcList!=null&&srcList.size()>0){
			if(!savePath.endsWith("/")){
				savePath += "/";
			}
			String src = null;
			int size = srcList.size();
			for(int i=0;i<size;i++){
				if(isstart){
					src = srcList.get(i);
					progress((int)(100*(float)i)/size);
					downLoadImage(src,savePath);
				}else{
					throw new StopException("终止下载!");
				}
			}
			//进度条满
			if(isstart){
				progress(100);
			}
		}
		else{
			log("资源地址为空，无可下载内容！");
		}
	}
	
	/**
	 * 下载图片
	 * @param src
	 * @param savePath
	 * @throws Exception
	 */
	public void downLoadImage(String src,String savePath) throws Exception{
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			URL url = new URL(src);
			String fileName = Md5Utils.getMd5(src) + getFileType(src);
			String filePath = savePath + fileName;
			if(!checkFileExists(filePath)){
				is = url.openStream();
				fos = new FileOutputStream(filePath);
				byte[] bytes = new byte[2048];
				int length = 0;
				while((length=is.read(bytes))>0){
					fos.write(bytes, 0, length);
				}
				log("文件名 : "+fileName+" - 下载成功!");
			}
			else{
				log("文件名 : "+fileName+" - 已经存在!");
			}
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
	
	/**
	 * 判断文件是否存在
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public boolean checkFileExists(String filePath) throws Exception{
		File file = new File(filePath);
		if(file.exists()){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取图片类型
	 * @param src
	 * @return
	 */
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
	 * 停止下载
	 */
	public void stop() {
		isstart = false;
		progress(0);
	}

	/**
	 * 批量下载
	 */
	@Override
	public abstract void downloadMore(String savePath, String url, String selector,
			String page, int start, int pageSize, int timeout, boolean first);
}
