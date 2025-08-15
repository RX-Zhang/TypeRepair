private void injectMockCandidate(Class<?> awaitingInjectionClazz, Set<Object> mocks, Object fieldInstance) {
    for (Field field : orderedInstanceFieldsFrom(awaitingInjectionClazz)) {
        Set<Object> filteredMocks = mockCandidateFilter.filterCandidate(mocks, field, fieldInstance).getFilteredMocks();
        mockCandidateFilter.filterCandidate(filteredMocks, field, fieldInstance).thenInject();
        mocks.removeAll(filteredMocks);
    }
}
