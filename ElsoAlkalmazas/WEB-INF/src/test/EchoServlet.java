package test;
 
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class EchoServlet extends HttpServlet {
 
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
				   
	  request.setCharacterEncoding("UTF-8");
	  response.setContentType("text/html; charset=UTF-8");
      
      PrintWriter out = response.getWriter();
      try {
         out.println("<!DOCTYPE html>");
         out.println("<html><head>");
         out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
         out.println("<title>Echo Servlet</title></head>");
         out.println("<body><h2>You have enter</h2>");
		// Kiszedjük a textfield értékét
         String username = request.getParameter("username");
         if (username == null || (username = htmlFilter(username.trim())).length() == 0) {
            out.println("<p>Name: MISSING</p>");
         } else {
            out.println("<p>Name: " + username + "</p>");
         }
         String password = request.getParameter("password");
         if (password == null
               || (password = htmlFilter(password.trim())).length() == 0) {
            out.println("<p>Password: MISSING</p>");
         } else {
            out.println("<p>Password: " + password + "</p>");
         }
         String gender = request.getParameter("gender");
         if (gender == null) {
            out.println("<p>Gender: MISSING</p>");
         } else if (gender.equals("m")) {
            out.println("<p>Gender: male</p>");
         } else {
            out.println("<p>Gender: female</p>");
         }
         String age = request.getParameter("age");
         if (age == null) {
            out.println("<p>Age: MISSING</p>");
         } else if (age.equals("1")) {
            out.println("<p>Age: &lt; 1 year old</p>");
         } else if (age.equals("99")) {
            out.println("<p>Age: 1 to 99 years old</p>");
         } else {
            out.println("<p>Age: &gt; 99 years old</p>");
         }
         String[] languages = request.getParameterValues("language");
         if (languages == null || languages.length == 0) {
            out.println("<p>Languages: NONE</p>");
         } else {
            out.println("<p>Languages: ");
            for (String language : languages) {
               if (language.equals("c")) {
                  out.println("C/C++ ");
               } else if (language.equals("cs")) {
                  out.println("C# ");
               } else if (language.equals("java")) {
                  out.println("Java ");
               }
            }
            out.println("</p>");
         }
         String instruction = request.getParameter("instruction");
         if (instruction == null || (instruction = htmlFilter(instruction.trim())).length() == 0 || instruction.equals("Enter your instruction here...")) {
            out.println("<p>Instruction: NONE</p>");
         } else {
            out.println("<p>Instruction: " + instruction + "</p>");
         }
         String secret = request.getParameter("secret");
         out.println("<p>Secret: " + secret + "</p>");
         Enumeration names = request.getParameterNames();
         out.println("<p>Request Parameter Names are: ");
         if (names.hasMoreElements()) {
            out.print(htmlFilter(names.nextElement().toString()));
         }
         do {
            out.print(", " + htmlFilter(names.nextElement().toString()));
         } while (names.hasMoreElements());
         out.println(".</p>");

         out.println("<a href='form.html'>BACK</a>");
 
         out.println("</body></html>");
      } finally {
         out.close();  
      }
   }
 
   /*Atirányíjuk a post kérést, hogy a doGet-et használja és ne kelljen duplikálni a kódot.*/
   @Override
   public void doPost(HttpServletRequest request, HttpServletResponse response)
               throws IOException, ServletException {
      request.setCharacterEncoding("UTF-8");
      doGet(request, response);
   }
 
   // Filter the string for special HTML characters to prevent
   // command injection attack
   private static String htmlFilter(String message) {
      if (message == null) return null;
      int len = message.length();
      StringBuffer result = new StringBuffer(len + 20);
      char aChar;
 
      for (int i = 0; i < len; ++i) {
         aChar = message.charAt(i);
         switch (aChar) {
             case '<': result.append("&lt;"); break;
             case '>': result.append("&gt;"); break;
             case '&': result.append("&amp;"); break;
             case '"': result.append("&quot;"); break;
             default: result.append(aChar);
         }
      }
      return (result.toString());
   }
}