private void injectMockCandidate(Class<?> awaitingInjectionClazz, Set<Object> mocks, Object fieldInstance) {
    for (Field field : orderedInstanceFieldsFrom(awaitingInjectionClazz)) {
        Set<Object> matchingMocks = mockCandidateFilter.filterCandidate(mocks, field, fieldInstance).getMatchingMocks();
        if (!matchingMocks.isEmpty()) {
            Object bestMatch = mockCandidateFilter.selectBestMatch(matchingMocks, field);
            mockCandidateFilter.injectMock(fieldInstance, field, bestMatch);
            mocks.remove(bestMatch);
        }
    }
}
