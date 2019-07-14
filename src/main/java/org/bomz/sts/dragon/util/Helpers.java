package org.bomz.sts.dragon.util;

/**
 * Contains static methods
 */
public class Helpers {

  private static final String MOD_ID = "ftlsoundtrack";

  public static String makeId(String s) {
        return String.format("%s:%s", MOD_ID, s);
    }

  public static String getImage(String filename) {
        return "img/cards/" + filename + ".png";
    }

  public static String attacksPlayedString(int attacks) {
      return " NL (" + attacks + " " + (attacks == 1 ? "Attack" : "Attacks") + " played.)";
  }

  private Helpers() {}
}
