public <T> void resetMock(T mock) {
    MockHandlerInterface<T> oldMockHandler = getMockHandler(mock);
    MockSettingsImpl settings = oldMockHandler.getMockSettings();
    MethodInterceptorFilter newFilter = newMethodInterceptorFilter(settings);
    ((Factory) mock).setCallback(0, newFilter);
}
