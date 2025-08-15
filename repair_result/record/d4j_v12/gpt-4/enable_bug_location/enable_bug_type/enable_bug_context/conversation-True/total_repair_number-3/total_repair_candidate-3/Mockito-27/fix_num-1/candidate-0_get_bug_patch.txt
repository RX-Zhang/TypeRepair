public <T> void resetMock(T mock) {
    MockHandlerInterface<T> oldMockHandler = getMockHandler(mock);
    MockSettingsImpl settings = ((MockSettingsImpl) org.mockito.Mockito.withSettings().defaultAnswer(org.mockito.Mockito.RETURNS_DEFAULTS));
    MethodInterceptorFilter newFilter = newMethodInterceptorFilter(settings);
    ((Factory) mock).setCallback(0, newFilter);
}
