package cn.et.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MyNews {
	/**
	 * 添加数据到数据库
	 * @param title
	 * @param content
	 * @param createtime
	 * @param htmlPath
	 * @throws SQLException
	 */
	public void insertNews(String title,String content,String createtime,String htmlPath) throws SQLException{
		String sql = "insert into mynews(title,content,createtime,htmlpath) values('"+title+"','"+content+"','"+createtime+"','"+htmlPath+"')";
		DbUtils.execute(sql);
	}
	
	/**
	 * 查询数据
	 * @return
	 * @throws SQLException
	 */
	public List<Map> selectNews() throws SQLException{
		String sql = "select * from mynews";
		return DbUtils.query(sql);
	}
}
