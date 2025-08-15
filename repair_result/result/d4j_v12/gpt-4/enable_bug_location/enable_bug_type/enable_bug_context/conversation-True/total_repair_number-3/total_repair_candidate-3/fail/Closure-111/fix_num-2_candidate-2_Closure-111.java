protected JSType caseTopType(JSType topType) {
  return restrictToArrayVisitor.visit(topType);
}
