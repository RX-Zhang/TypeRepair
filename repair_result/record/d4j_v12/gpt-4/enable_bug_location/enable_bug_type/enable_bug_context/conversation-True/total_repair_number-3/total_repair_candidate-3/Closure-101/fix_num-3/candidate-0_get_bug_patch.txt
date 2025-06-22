protected CompilerOptions createOptions() {
  CompilerOptions options = new CompilerOptions();
  CompilerOptions.Builder builder = new CompilerOptions.Builder();
  
  CompilerOptions optsFromLevel = new CompilerOptions();
  CompilationLevel level = flags.compilation_level;
  level.setOptionsForCompilationLevel(optsFromLevel);
  if (flags.debug) {
    level.setDebugOptionsForCompilationLevel(optsFromLevel);
  }

  WarningLevel wLevel = flags.warning_level;
  wLevel.setOptionsForWarningLevel(optsFromLevel);
  for (FormattingOption formattingOption : flags.formatting) {
    formattingOption.applyToOptions(optsFromLevel);
  }
  
  // Copy all fields from optsFromLevel to options
  options = optsFromLevel;

  if (flags.process_closure_primitives) {
    options.closurePass = true;
  } else {
    options.closurePass = false;
  }

  initOptionsFromFlags(options);
  return options;
}
