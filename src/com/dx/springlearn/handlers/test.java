package com.dx.springlearn.handlers;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class test {
	@RequestMapping("/test")
	public  void hello() {
		String resource="resources/config/conf.xml";
   	    InputStream is = HelloWord.class.getClassLoader().getResourceAsStream(resource);
   	    System.out.println(is);
   	    SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
   	    SqlSession openSession = sessionFactory.openSession();
   	    System.out.println(openSession);
   	    String statement="com.dx.springlearn.handlers.dao.UserDao.queryAll";
   	    List<Object> selectList = openSession.selectList(statement);
   	    System.out.println(selectList.isEmpty());
   	    for(Object u : selectList) {
   		   System.out.println(u);
   	    }
	}
	
}
