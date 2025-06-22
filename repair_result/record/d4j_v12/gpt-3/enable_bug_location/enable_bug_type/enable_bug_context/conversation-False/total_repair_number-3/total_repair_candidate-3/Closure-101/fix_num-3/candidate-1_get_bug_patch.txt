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
  
  // Correcting the logical expression for process_closure_primitives
  // to ensure that the closure pass is only enabled when it is true.
  if (flags.process_closure_primitives) {
    options.closurePass = true;
  } else {
    options.closurePass = false; // Ensure closurePass is set to false when the flag is false
  }

  initOptionsFromFlags(options);
  return options;
}
