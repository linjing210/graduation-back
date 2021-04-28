package cn.edu.aiit.gradution.util;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ExcelUtils {
	public static void export(HttpServletResponse response, String fileName, List<Map<String, Object>> data){
		// 通过工具类创建writer，创建xlsx格式
		ExcelWriter writer = ExcelUtil.getWriter(true);
		// 一次性写出内容，使用默认样式，强制输出标题
		writer.write(data);
		//response为HttpServletResponse对象
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		//topic.xlsx是弹出下载对话框的文件名，不能为中文，中文需自行编码
		response.setHeader("Content-Disposition","attachment;filename=" + fileName);
		//out为OutputStream，需要写出到的目标流
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			writer.flush(out, true);
			// 关闭writer，释放内存
			writer.close();
			//此处记得关闭输出Servlet流
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
