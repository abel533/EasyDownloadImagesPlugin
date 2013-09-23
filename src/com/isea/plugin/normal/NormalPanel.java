package com.isea.plugin.normal;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.isea.basic.BasePanel;
import javax.swing.border.EmptyBorder;


public class NormalPanel extends BasePanel {
	private static final long serialVersionUID = 9080426160199827735L;
	private JTextField url;
	private JTextField page;
	private JTextField selector;
	private JTextField pageSize;
	private JRadioButton firstRadio;
	private JTextField start;
	
	public NormalPanel() {
		setBorder(new EmptyBorder(2, 4, 2, 0));
		setLayout(null);
		
		JLabel lblhttpwwwblogcompage = new JLabel("该模式针对地址为：http://www.***.com/page/1 类似地址的网站");
		lblhttpwwwblogcompage.setBounds(69, 10, 431, 26);
		add(lblhttpwwwblogcompage);
		lblhttpwwwblogcompage.setHorizontalAlignment(SwingConstants.CENTER);
		lblhttpwwwblogcompage.setPreferredSize(new Dimension(200, 26));
		
		firstRadio = new JRadioButton("首地址不带页码");
		firstRadio.setSelected(true);
		firstRadio.setBounds(70, 130, 116, 23);
		add(firstRadio);
		
		JLabel label_1 = new JLabel("首地址：");
		label_1.setBounds(0, 135, 52, 15);
		add(label_1);
		
		JLabel lblNewLabel = new JLabel("基础地址：");
		lblNewLabel.setBounds(0, 54, 65, 15);
		add(lblNewLabel);
		
		url = new JTextField();
		url.setBounds(70, 46, 420, 30);
		add(url);
		url.setColumns(10);
		
		JLabel label = new JLabel("连续地址：");
		label.setBounds(0, 95, 65, 15);
		add(label);
		
		page = new JTextField();
		page.setBounds(70, 87, 420, 30);
		add(page);
		page.setText("a");
		page.setColumns(10);
		
		JLabel label_2 = new JLabel("图片选择器：");
		label_2.setBounds(0, 170, 86, 15);
		add(label_2);
		
		selector = new JTextField();
		selector.setBounds(70, 163, 420, 30);
		add(selector);
		selector.setColumns(10);
		
		JLabel label_3 = new JLabel("页码限制：");
		label_3.setBounds(360, 135, 69, 15);
		add(label_3);
		
		pageSize = new JTextField();
		pageSize.setHorizontalAlignment(SwingConstants.RIGHT);
		pageSize.setText("1");
		pageSize.setBounds(435, 127, 55, 30);
		add(pageSize);
		pageSize.setColumns(10);
		
		JLabel label_4 = new JLabel("起始页码：");
		label_4.setBounds(200, 135, 69, 15);
		add(label_4);
		
		start = new JTextField();
		start.setHorizontalAlignment(SwingConstants.RIGHT);
		start.setText("1");
		start.setBounds(280, 126, 55, 30);
		add(start);
		start.setColumns(10);

	}

	/**
	 * 下载按钮执行的方法
	 */
	@Override
	public void download(String savePath, int timeout) {
		if(!checkField()){
			return;
		}
		progress(0);
		log("开始下载");
		log("保存路径:"+savePath);
		this.setDownloadHelper(new NormalDownLoad());
		downloadHelper.downloadMore(savePath, 
				url.getText(), 
				selector.getText(), 
				page.getText(),
				Integer.parseInt(start.getText()),
				Integer.parseInt(pageSize.getText()), 
				timeout,
				firstRadio.isSelected());
	}
	/**
	 * 校验用户填入信息
	 */
	private boolean checkField(){
		try {
			if(url.getText().equals("")){
				throw new Exception("基础地址不能为空");
			}
			if(selector.getText().equals("")){
				throw new Exception("图片选择器不能为空");
			}
			if(start.getText().equals("")){
				throw new Exception("起始页码不能为空");
			}
			if(pageSize.getText().equals("")){
				throw new Exception("页码限制不能为空");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(NormalPanel.this, e.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	@Override
	public void clear() {
		//清空界面信息
		url.setText("");
		selector.setText("");
		page.setText("");
		start.setText("");
		pageSize.setText("");
		firstRadio.setSelected(true);
	}

	@Override
	public void stop() {
		//停止下载
		if(downloadHelper!=null){
			downloadHelper.stop();
		}
	}
}
