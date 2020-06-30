package test;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Properties;
import java.util.Base64;
import java.lang.*;

@WebServlet(name = "feldolgozas", urlPatterns = {"/feldolgozas/*"})
public class HelloServlet extends HttpServlet {
    //A config fájl útvonala:
	//Elmenthetem környezeti változóba is e helyett és akkor csak kiszedem az initben
	//private static final String LOCATION = "C:\\Program Files\\Java\\xamppJavaTomcat\\tomcat\\webapps\\ElsoAlkalmazas\\WEB-INF\\config.conf";
	
	 private String username = "";
	 private String password = "";
	 
	// Ez ugyanazt az értéket fogja mutatni mindenkinél, aki meghívja, mert csak egyszer jön létre a servlet.
	private double something = Math.random();

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws IOException, ServletException {
			 
	  //basic autentikáció ellenőrzése:
	  boolean success = checkAuth(request);
	  System.out.println(success);
	  if(success){
		  	  //beállítjuk a válasz MIME típusát
      response.setContentType("text/html;charset=UTF-8");
	  //a response objektumnak a getWriter eszközét fogjuk használni, ezzel adhatunk tartalmat a kimenő válaszunkhoz.
      PrintWriter out = response.getWriter();
	try{
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head><title>Hello, World</title></head>");
      out.println("<body>");
      out.println("<h1>Hello, world!</h1>");  
      out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
      out.println("<p>Protocol: " + request.getProtocol() + "</p>");
      out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
      out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>"); 
      out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
      out.println("</body></html>");
		}finally{
      out.close();  
				}
	  }else{
		  noAccess(response);
	  }

   }
   
   private void noAccess(HttpServletResponse response){
	   try{
		   response.sendError(401);
	   }catch(Exception e){
		   System.out.println(e);
	   }
	   
   }
   
   private boolean checkAuth(HttpServletRequest request){
	   
	   //kiszedjük a request objektumból az Authorization key-hez tartozó értéket. Ez mindig így fog szerepelni a request headerjében.
	   String authValue = request.getHeader("Authorization");
	   if(authValue == null){
		   return false;
	   }else{
		   //a basic authentication mindig így kezdődik.
		   if(!authValue.toUpperCase().startsWith("BASIC ")){
			   return false;
		   }else{
			   //dekódoljuk és levágjuk az első hat karaktert: "BASIC " --> így marad a user:jelszó páros
			   String userpass = new String(Base64.getDecoder().decode(authValue.substring(6)));
			   int counter = userpass.indexOf(":");
			   String bejovoUser = userpass.substring(0,counter); //az első értéket beleszámolja, az utolsót már nem teszi bele.
			   String bejovoPass = userpass.substring(counter+1);
			   if(bejovoUser.equals(username) && bejovoPass.equals(password)){
				   return true;
			   }
		   }
	   }
	   return false;
   }
   
   @Override
   public void init(){
	   DB db = new DB();
	   //ha a konfigurációs fájlom környezeti változóba mentettem és így adom meg
	   String LOCATION = System.getenv("CONFIG");
	   System.out.println(LOCATION);
	   try{	   
	   Properties pr = new Properties();
	   //megmondjuk hol található a fájl
	   File config = new File(LOCATION);
	   //létezik-e és olvasható-e
	   if(config.exists() && config.canRead()){
		   System.out.println("A fájlból kiszedett adatok: ");
		   pr.load(new FileInputStream(config));
		   username = pr.getProperty("dbusername");
		   password = pr.getProperty("dbpassword");
		   System.out.println(username + " " + password);
		   
	   }else{
		   System.out.println("Nincs meg a fájl.");
	   }
	   }catch(Exception e){
		   e.printStackTrace(System.err);
	   }
   }
   
   @Override 
   public void destroy(){
	   
   }
   
   @Override
   public String getServletInfo(){
	   return "test";
}
}