protected JSType caseTopType(JSType topType) {
  if (topType != null && topType.isArrayType()) {
    return ARRAY_TYPE; // Return ARRAY_TYPE if topType is an array
  }
  return topType; // Otherwise, return the original topType
}
