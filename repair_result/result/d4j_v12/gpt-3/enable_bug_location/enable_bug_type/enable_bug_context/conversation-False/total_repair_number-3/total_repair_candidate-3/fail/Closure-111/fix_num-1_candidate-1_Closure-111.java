protected JSType caseTopType(JSType topType) {
  // Check if the topType is an instance of ARRAY_TYPE
  if (topType.isSubtype(ARRAY_TYPE)) {
    return ARRAY_TYPE; // Return ARRAY_TYPE if topType is an array
  }
  return topType; // Otherwise, return the original topType
}
