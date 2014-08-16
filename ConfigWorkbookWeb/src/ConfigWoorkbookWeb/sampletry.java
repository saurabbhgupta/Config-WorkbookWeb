package ConfigWoorkbookWeb;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
public class sampletry {
	
	public void fun(){
		System.out.println("in class fuction");
		
		
	}
	public String Openpage() throws IOException{
	    // here type your JSP page that you want to open
		System.out.println("in openpage fuction");
		
	    //res.sendRedirect("NewFile.jsp");
		return ("10");
	    }
	public String Openpage1() throws IOException{
	    // here type your JSP page that you want to open
		System.out.println("in openpage fuction");
	    //res.sendRedirect("NewFile.jsp");
		return ("20");
	    }

}
