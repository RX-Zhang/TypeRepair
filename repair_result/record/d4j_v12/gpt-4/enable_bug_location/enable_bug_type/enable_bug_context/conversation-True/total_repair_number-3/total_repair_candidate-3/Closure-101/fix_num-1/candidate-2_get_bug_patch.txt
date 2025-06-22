protected CompilerOptions createOptions() {
  CompilerOptions options = new CompilerOptions();
  CompilerOptions baseOptions = new CompilerOptions();
  CompilationLevel level = flags.compilation_level;
  level.setOptionsForCompilationLevel(baseOptions);
  if (flags.debug) {
    level.setDebugOptionsForCompilationLevel(baseOptions);
  }

  WarningLevel wLevel = flags.warning_level;
  wLevel.setOptionsForWarningLevel(baseOptions);

  for (FormattingOption formattingOption : flags.formatting) {
    formattingOption.applyToOptions(baseOptions);
  }

  if (flags.process_closure_primitives) {
    baseOptions.closurePass = true;
  } else {
    options.closurePass = false;
  }

  // Copy all fields from baseOptions to options to ensure correct settings
  options = baseOptions;

  initOptionsFromFlags(options);

  return options;
}
