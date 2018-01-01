package cn.et.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.et.model.MyNews;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Servlet implementation class NewsServlet
 */
public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public NewsServlet() {
        // TODO Auto-generated constructor stub
    }
    
    //存放HTML的位置
    public static final String HTML_DIR="E:\\newsHtml";
    MyNews news = new MyNews();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//获取请求数据
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		String createtime = sdf.format(date);
		String uuid = UUID.randomUUID().toString();
		
		try {
			//生成html
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
			//设置查找模板文件的位置
			cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
			cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_0));
			
			//将数据存入Map集合
			Map map = new HashMap();
			map.put("title", title);
			map.put("createtime", createtime);
			map.put("content", content);

			//创建html文件保存路径
			String htmlPath =  HTML_DIR+"/"+uuid+".html";
			//HTML文件存入数据库的路径
			String savePath = uuid+".html";
			//通过模板将数据生成html文件
			Template temp = cfg.getTemplate("newsdetail.ftl");
			//输出html
			Writer out = new OutputStreamWriter(new FileOutputStream(htmlPath));
			temp.process(map, out);
			out.flush();
			out.close();
			
			//将数据添加到数据库
			news.insertNews(title, content, createtime, savePath);
			response.getWriter().println("发布成功");
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
