private void init() {
    thisYear = Calendar.getInstance(timeZone, locale).get(Calendar.YEAR);

    nameValues = new ConcurrentHashMap<Integer, KeyValue[]>();

    StringBuilder regex = new StringBuilder();
    List<Strategy> collector = new ArrayList<Strategy>();

    Matcher patternMatcher = formatPattern.matcher(pattern);
    if (!patternMatcher.lookingAt()) {
        throw new IllegalArgumentException("Invalid pattern");
    }

    currentFormatField = patternMatcher.group();
    Strategy currentStrategy = getStrategy(currentFormatField);
    for (;;) {
        patternMatcher.region(patternMatcher.end(), patternMatcher.regionEnd());
        if (!patternMatcher.lookingAt()) {
            nextStrategy = null;
            break;
        }
        String nextFormatField = patternMatcher.group();
        nextStrategy = getStrategy(nextFormatField);
        // Append the regex for the current strategy, properly escaping literal strings including quotes
        if (currentFormatField.charAt(0) == '\'') {
            escapeRegex(regex, currentFormatField.substring(1, currentFormatField.length() - 1), true);
        } else if (currentStrategy.addRegex(this, regex)) {
            collector.add(currentStrategy);
        }
        currentFormatField = nextFormatField;
        currentStrategy = nextStrategy;
    }
    // Append the regex for the last strategy
    if (currentFormatField != null) {
        if (currentFormatField.charAt(0) == '\'') {
            escapeRegex(regex, currentFormatField.substring(1, currentFormatField.length() - 1), true);
        } else if (currentStrategy.addRegex(this, regex)) {
            collector.add(currentStrategy);
        }
    }
    currentFormatField = null;
    strategies = collector.toArray(new Strategy[collector.size()]);
    parsePattern = Pattern.compile(regex.toString());
}
