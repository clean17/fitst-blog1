package shop.mtcoding.blog.util;

public class Script {
    public static String href(String msg){
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("location.href='"+msg+"'");
        sb.append("</script>");
        String returnString = sb.toString();
        return returnString;
    }
}
