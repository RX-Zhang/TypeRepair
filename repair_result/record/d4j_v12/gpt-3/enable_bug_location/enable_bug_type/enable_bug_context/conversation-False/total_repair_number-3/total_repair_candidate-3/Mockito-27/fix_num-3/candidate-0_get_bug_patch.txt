public <T> void resetMock(T mock) {
    MockHandlerInterface<T> oldMockHandler = getMockHandler(mock);
    MockSettingsImpl oldSettings = ((MockHandler<?>) oldMockHandler).getMockSettings();
    MockHandler<T> newMockHandler = new MockHandler<T>(oldMockHandler);
    MethodInterceptorFilter newFilter = new MethodInterceptorFilter(newMockHandler, oldSettings);
    ((Factory) mock).setCallback(0, newFilter);
}
