private void init() {
    thisYear= Calendar.getInstance(timeZone, locale).get(Calendar.YEAR);

    nameValues= new ConcurrentHashMap<Integer, KeyValue[]>();

    StringBuilder regex= new StringBuilder();
    List<Strategy> collector = new ArrayList<Strategy>();

    Matcher patternMatcher= formatPattern.matcher(pattern);
    if(!patternMatcher.lookingAt()) {
        throw new IllegalArgumentException("Invalid pattern");
    }

    currentFormatField= patternMatcher.group();
    Strategy currentStrategy= getStrategy(currentFormatField);
    for(;;) {
        patternMatcher.region(patternMatcher.end(), patternMatcher.regionEnd());
        if(!patternMatcher.lookingAt()) {
            nextStrategy = null;
            break;
        }
        String nextFormatField= patternMatcher.group();
        nextStrategy = getStrategy(nextFormatField);
        if(currentStrategy.addRegex(this, regex)) {
            collector.add(currentStrategy);
        }
        currentFormatField= nextFormatField;
        currentStrategy= nextStrategy;
    }

    // Validate that all quoted literals are properly balanced
    if (currentFormatField != null && currentFormatField.length() > 0 && currentFormatField.charAt(0) == '\'') {
        // Check for unterminated quote by verifying length and last char
        if (currentFormatField.length() == 1 || currentFormatField.charAt(currentFormatField.length() - 1) != '\'') {
            throw new IllegalArgumentException("Unterminated quote in pattern: " + pattern);
        }
    }

    if(currentStrategy.addRegex(this, regex)) {
        collector.add(currentStrategy);
    }
    currentFormatField= null;
    strategies= collector.toArray(new Strategy[collector.size()]);
    parsePattern= Pattern.compile(regex.toString());
}
