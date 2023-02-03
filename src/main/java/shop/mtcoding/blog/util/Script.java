package shop.mtcoding.blog.util;

public class Script {
    public static String href(String href){
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("location.href='"+href+"'");
        sb.append("</script>");
        String returnString = sb.toString();
        return returnString;
    }
    public static String hrefAlert(String msg, String path){
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append("alert('"+msg+"');");
        sb.append("location.href='"+path+"';");
        sb.append("</script>");
        String returnString = sb.toString();
        return returnString;
    }
}
