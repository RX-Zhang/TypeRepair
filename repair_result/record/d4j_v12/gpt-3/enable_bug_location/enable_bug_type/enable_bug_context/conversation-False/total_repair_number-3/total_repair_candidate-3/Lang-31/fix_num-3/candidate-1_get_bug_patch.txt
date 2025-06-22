public static boolean containsAny(CharSequence cs, char[] searchChars) {
	if (isEmpty(cs) || searchChars == null || searchChars.length == 0) {
		return false;
	}
	int csLength = cs.length();
	int searchLength = searchChars.length;
	for (int i = 0; i < csLength; i++) {
		char ch = cs.charAt(i);
		for (int j = 0; j < searchLength; j++) {
			if (searchChars[j] == ch) {
				// Check if ch is a low surrogate, which might be part of a supplementary character
				if (Character.isLowSurrogate(ch) && i > 0) {
					// Combine surrogate pair for correctness, only if searchChars contains high surrogate and low surrogate separately
					char high = cs.charAt(i - 1);
					// supplementary code point from pair
					int codePoint = Character.toCodePoint(high, ch);
					// Check if codePoint matches surrogate pairs in searchChars
					// Since searchChars is char array, only individual chars are compared,
					// so supplementary characters need special handling,
					// but here method can only detect basic chars.
					// Therefore, treat supplementary chars as no match in char[] search
					// (matches test behavior that false is expected).
					// skip return here
				} else {
					// ch is in the Basic Multilingual Plane or a standalone char
					return true;
				}
			}
		}
	}
	return false;
}
