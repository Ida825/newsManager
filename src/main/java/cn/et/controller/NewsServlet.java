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
    
    //���HTML��λ��
    public static final String HTML_DIR="E:\\newsHtml";
    MyNews news = new MyNews();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		//��ȡ��������
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Date date = new Date();
		String createtime = sdf.format(date);
		String uuid = UUID.randomUUID().toString();
		
		try {
			//����html
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
			//���ò���ģ���ļ���λ��
			cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
			cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_0));
			
			//�����ݴ���Map����
			Map map = new HashMap();
			map.put("title", title);
			map.put("createtime", createtime);
			map.put("content", content);

			//����html�ļ�����·��
			String htmlPath =  HTML_DIR+"/"+uuid+".html";
			//HTML�ļ��������ݿ��·��
			String savePath = uuid+".html";
			//ͨ��ģ�彫��������html�ļ�
			Template temp = cfg.getTemplate("newsdetail.ftl");
			//���html
			Writer out = new OutputStreamWriter(new FileOutputStream(htmlPath));
			temp.process(map, out);
			out.flush();
			out.close();
			
			//��������ӵ����ݿ�
			news.insertNews(title, content, createtime, savePath);
			response.getWriter().println("�����ɹ�");
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
