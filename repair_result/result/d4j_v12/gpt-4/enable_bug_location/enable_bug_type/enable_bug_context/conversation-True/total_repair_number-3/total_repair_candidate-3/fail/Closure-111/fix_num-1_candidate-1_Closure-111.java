protected JSType caseTopType(JSType topType) {
  // Instead of returning topType, restrict to array type if possible
  return restrictToArrayVisitor.caseTopType(topType);
}
