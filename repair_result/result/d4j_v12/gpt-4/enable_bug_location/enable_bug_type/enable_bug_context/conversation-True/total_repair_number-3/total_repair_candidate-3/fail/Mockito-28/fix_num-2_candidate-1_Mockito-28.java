private void injectMockCandidate(Class<?> awaitingInjectionClazz, Set<Object> mocks, Object fieldInstance) {
    for (Field field : orderedInstanceFieldsFrom(awaitingInjectionClazz)) {
        Iterator<Object> mocksIterator = mocks.iterator();
        while (mocksIterator.hasNext()) {
            Object mock = mocksIterator.next();
            if (mockCandidateFilter.filterCandidate(mock, field, fieldInstance).thenInject()) {
                mocksIterator.remove();
            }
        }
    }
}
