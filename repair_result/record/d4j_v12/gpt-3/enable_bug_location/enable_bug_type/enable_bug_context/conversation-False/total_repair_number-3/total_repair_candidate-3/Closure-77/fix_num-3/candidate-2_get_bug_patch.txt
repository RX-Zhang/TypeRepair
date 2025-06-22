static String strEscape(String s, char quote,
                        String doublequoteEscape,
                        String singlequoteEscape,
                        String backslashEscape,
                        CharsetEncoder outputCharsetEncoder) {
  StringBuilder sb = new StringBuilder(s.length() + 2);
  sb.append(quote);
  for (int i = 0; i < s.length(); i++) {
    char c = s.charAt(i);
    switch (c) {
      case '\n': sb.append("\\n"); break;
      case '\r': sb.append("\\r"); break;
      case '\t': sb.append("\\t"); break;
      case '\\': sb.append(backslashEscape); break;
      case '\"': sb.append(doublequoteEscape); break;
      case '\'': sb.append(singlequoteEscape); break;
      case '>':                       // Break --> into --\> or ]]> into ]]\>
        if (i >= 2 &&
            ((s.charAt(i - 1) == '-' && s.charAt(i - 2) == '-') ||
             (s.charAt(i - 1) == ']' && s.charAt(i - 2) == ']'))) {
          sb.append("\\>");
        } else {
          sb.append(c);
        }
        break;
      case '<':
        // Break </script into <\/script
        final String END_SCRIPT = "/script";

        // Break <!-- into <\!--
        final String START_COMMENT = "!--";

        if (s.regionMatches(true, i + 1, END_SCRIPT, 0,
                            END_SCRIPT.length())) {
          sb.append("<\\");
          i += END_SCRIPT.length() - 1; // Skip past the end script tag
        } else if (s.regionMatches(false, i + 1, START_COMMENT, 0,
                                   START_COMMENT.length())) {
          sb.append("<\\");
          i += START_COMMENT.length() - 1; // Skip past the comment tag
        } else {
          sb.append(c);
        }
        break;
      default:
        // If we're given an outputCharsetEncoder, then check if the
        //  character can be represented in this character set.
        if (outputCharsetEncoder != null) {
          if (outputCharsetEncoder.canEncode(c)) {
            sb.append(c);
          } else {
            // Unicode-escape the character.
            appendHexJavaScriptRepresentation(sb, c);
          }
        } else {
          // No charsetEncoder provided - pass straight latin characters
          // through, and escape the rest.  Doing the explicit character
          // check is measurably faster than using the CharsetEncoder.
          if (c > 0x1f && c <= 0x7f) {
            sb.append(c);
          } else {
            // Other characters can be misinterpreted by some js parsers,
            // or perhaps mangled by proxies along the way,
            // so we play it safe and unicode escape them.
            appendHexJavaScriptRepresentation(sb, c);
          }
        }
    }
  }
  sb.append(quote);
  return sb.toString();
}
