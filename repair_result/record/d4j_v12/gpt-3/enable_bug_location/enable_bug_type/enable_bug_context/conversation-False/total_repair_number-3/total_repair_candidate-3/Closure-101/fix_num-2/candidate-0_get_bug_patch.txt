protected CompilerOptions createOptions() {
  CompilerOptions options = new CompilerOptions();
  options.setCodingConvention(new ClosureCodingConvention());
  CompilationLevel level = flags.compilation_level;
  level.setOptionsForCompilationLevel(options);
  if (flags.debug) {
    level.setDebugOptionsForCompilationLevel(options);
  }

  WarningLevel wLevel = flags.warning_level;
  wLevel.setOptionsForWarningLevel(options);
  for (FormattingOption formattingOption : flags.formatting) {
    formattingOption.applyToOptions(options);
  }
  // Corrected logical expression to ensure closurePass is set correctly
  if (flags.process_closure_primitives) {
    options.closurePass = true;
  } else {
    options.closurePass = false; // Ensure closurePass is explicitly set to false if not processing primitives
  }

  initOptionsFromFlags(options);
  return options;
}
