private void injectMockCandidate(Class<?> awaitingInjectionClazz, Set<Object> mocks, Object fieldInstance) {
    for(Field field : orderedInstanceFieldsFrom(awaitingInjectionClazz)) {
        if (mockCandidateFilter.filterCandidate(mocks, field, fieldInstance).thenInject()) {
            // Since a mock was injected into this field, no need to continue injecting other mocks into the same field
            break;
        }
    }
}
