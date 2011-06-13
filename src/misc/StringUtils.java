package misc;

import java.util.*;

public class StringUtils {

  private StringUtils() {}

  private static HashMap<String,String> htmlEntities;
  static {
    htmlEntities = new HashMap<String,String>();
    htmlEntities.put("&lt;","<")    ; htmlEntities.put("&gt;",">");
    htmlEntities.put("&amp;","&")   ; htmlEntities.put("&quot;","\"");
    htmlEntities.put("&agrave;","�"); htmlEntities.put("&Agrave;","�");
    htmlEntities.put("&acirc;","�") ; htmlEntities.put("&auml;","�");
    htmlEntities.put("&Auml;","�")  ; htmlEntities.put("&Acirc;","�");
    htmlEntities.put("&aring;","�") ; htmlEntities.put("&Aring;","?");
    htmlEntities.put("&aelig;","�") ; htmlEntities.put("&AElig;","�" );
    htmlEntities.put("&ccedil;","?"); htmlEntities.put("&Ccedil;","�");
    htmlEntities.put("&eacute;","�"); htmlEntities.put("&Eacute;","�" );
    htmlEntities.put("&egrave;","?"); htmlEntities.put("&Egrave;","�");
    htmlEntities.put("&ecirc;","?") ; htmlEntities.put("&Ecirc;","�");
    htmlEntities.put("&euml;","�")  ; htmlEntities.put("&Euml;","�");
    htmlEntities.put("&iuml;","�")  ; htmlEntities.put("&Iuml;","�");
    htmlEntities.put("&ocirc;","�") ; htmlEntities.put("&Ocirc;","�");
    htmlEntities.put("&ouml;","�")  ; htmlEntities.put("&Ouml;","�");
    htmlEntities.put("&oslash;","�") ; htmlEntities.put("&Oslash;","�");
    htmlEntities.put("&szlig;","�") ; htmlEntities.put("&ugrave;","?");
    htmlEntities.put("&Ugrave;","�"); htmlEntities.put("&ucirc;","�");
    htmlEntities.put("&Ucirc;","�") ; htmlEntities.put("&uuml;","�");
    htmlEntities.put("&Uuml;","�")  ; htmlEntities.put("&nbsp;"," ");
    htmlEntities.put("&copy;","\u00a9");
    htmlEntities.put("&reg;","\u00ae");
    htmlEntities.put("&euro;","\u20a0");
  }

/*
   Here the original recursive version.
   It is fine unless you pass a big string then a Stack Overflow is possible :-(


  public static final String unescapeHTML(String source, int start){
     int i,j;

     i = source.indexOf("&", start);
     if (i > -1) {
        j = source.indexOf(";" ,i);
        if (j > i) {
           String entityToLookFor = source.substring(i , j + 1);
           String value = (String)htmlEntities.get(entityToLookFor);
           if (value != null) {
             source = new StringBuffer().append(source.substring(0 , i))
                                   .append(value)
                                   .append(source.substring(j + 1))
                                   .toString();
             return unescapeHTML(source, i + 1); // recursive call
           }
         }
     }
     return source;
  }

   M. McNeely Jr. has sent a version with do...while()loop which is more robust.
   Thanks to him!
*/

  public static final String unescapeHTML(String source) {
      int i, j;

      boolean continueLoop;
      int skip = 0;
      do {
         continueLoop = false;
         i = source.indexOf("&", skip);
         if (i > -1) {
           j = source.indexOf(";", i);
           if (j > i) {
             String entityToLookFor = source.substring(i, j + 1);
             String value = (String) htmlEntities.get(entityToLookFor);
             if (value != null) {
               source = source.substring(0, i)
                        + value + source.substring(j + 1);
               continueLoop = true;
             }
             else if (value == null){
                skip = i+1;
                continueLoop = true;
             }
           }
         }
      } while (continueLoop);
      return source;
  }

  public static void main(String args[]) throws Exception {
      // to see accented character to the console (Windows DOS Shell)
      java.io.PrintStream ps = new java.io.PrintStream(System.out, true, "Cp850");
      String test = "&copy; 2007  R&eacute;al Gagnon &lt;www.rgagnon.com&gt;";
      ps.println(test + "\n-->\n" +unescapeHTML(test));

      /*
         output ((Windows DOS Shell):
         &copy; 2007  R&eacute;al Gagnon &lt;www.rgagnon.com&gt;
         -->
         � 2007  R�al Gagnon <www.rgagnon.com>
      */
  }
}