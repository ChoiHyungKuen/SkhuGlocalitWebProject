package listeners;

// 페이지 컨트롤러 객체 준비
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import bookDreamDao.MySqlGiveInBookDreamDao;
import bookDreamDao.MySqlRequestInBookDreamDao;
import fcmConfigure.MySqlTokenInFcmDao;
import memberDao.MySqlMemberDao;
import noticeBoardDao.MySqlFreeBoardDao;
import noticeBoardDao.MySqlInfoBoardDao;
import recommendDao.MySqlRecommendDao;


@WebListener
public class ContextLoaderListener implements ServletContextListener {
	@Override
  public void contextInitialized(ServletContextEvent event) {
    try {
      ServletContext sc = event.getServletContext();

      InitialContext initialContext = new InitialContext();
      DataSource glocalDs = (DataSource)initialContext.lookup(		// glocalit_db에 대한 ds
          "java:comp/env/jdbc/glocalit_db");
      DataSource bookdreamDs = (DataSource)initialContext.lookup(		// bookdreamDs에 대한 ds
          "java:comp/env/jdbc/bookdream_db");
      DataSource fcmDs = (DataSource)initialContext.lookup(		// fcmDs에 대한 ds
              "java:comp/env/jdbc/fcm_db");
      DataSource noticeboardDs = (DataSource)initialContext.lookup(		// noticeboard
              "java:comp/env/jdbc/noticeboard_db");	
     
      
      // 각가의 ds를 dao에 주입하고 그것을 ServletContext 저장한다.
      MySqlMemberDao memberDao = new MySqlMemberDao();
      memberDao.setDataSource(glocalDs);
      memberDao.init();
      sc.setAttribute("memberDao", memberDao);
      
      MySqlRecommendDao recommendDao = new MySqlRecommendDao();
      recommendDao.setDataSource(glocalDs);
      recommendDao.init();
      sc.setAttribute("recommendDao", recommendDao);
	  
      MySqlRequestInBookDreamDao requestInBookDreamDao = new MySqlRequestInBookDreamDao();
      requestInBookDreamDao.setDataSource(bookdreamDs);
      requestInBookDreamDao.init();
      sc.setAttribute("requestInBookDreamDao", requestInBookDreamDao);
      

      MySqlGiveInBookDreamDao giveInBookDreamDao = new MySqlGiveInBookDreamDao();
      giveInBookDreamDao.setDataSource(bookdreamDs);
      giveInBookDreamDao.init();
      sc.setAttribute("giveInBookDreamDao", giveInBookDreamDao);
      
      MySqlTokenInFcmDao tokenInFcmDao = new MySqlTokenInFcmDao();
      tokenInFcmDao.setDataSource(fcmDs);
      tokenInFcmDao.init();
      sc.setAttribute("tokenInFcmDao", tokenInFcmDao);
      
      
      //자유게시판 
      MySqlFreeBoardDao freeboardDao = new MySqlFreeBoardDao();
      freeboardDao.setDataSource(noticeboardDs);
      freeboardDao.init();
      sc.setAttribute("freeboardDao", freeboardDao);
      
      //정보게시판
      MySqlInfoBoardDao infoboardDao = new MySqlInfoBoardDao();
      infoboardDao.setDataSource(noticeboardDs);
      infoboardDao.init();
      sc.setAttribute("infoboardDao", infoboardDao);
      
      
      
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {}
}
