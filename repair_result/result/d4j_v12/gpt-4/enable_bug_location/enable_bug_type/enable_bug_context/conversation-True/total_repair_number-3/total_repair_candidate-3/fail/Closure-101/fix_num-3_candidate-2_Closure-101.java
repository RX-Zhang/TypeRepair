protected CompilerOptions createOptions() {
  CompilerOptions options = new CompilerOptions();
  CompilerLevel level = flags.compilation_level;
  level.setOptionsForCompilationLevel(options);
  if (flags.debug) {
    level.setDebugOptionsForCompilationLevel(options);
  }

  WarningLevel wLevel = flags.warning_level;
  wLevel.setOptionsForWarningLevel(options);
  for (FormattingOption formattingOption : flags.formatting) {
    formattingOption.applyToOptions(options);
  }

  // Correction: Respect the process_closure_primitives flag correctly.
  if (!flags.process_closure_primitives) {
    options.closurePass = false;
  } else {
    options.closurePass = true;
  }

  initOptionsFromFlags(options);
  return options;
}
