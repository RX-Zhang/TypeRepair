public <T> void resetMock(T mock) {
    MockHandlerInterface<T> oldMockHandler = getMockHandler(mock);
    MockSettingsImpl settings = ((MockHandler<T>) oldMockHandler).getMockSettings();
    MockHandler<T> newMockHandler = new MockHandler<T>(oldMockHandler);
    MethodInterceptorFilter newFilter = new MethodInterceptorFilter(newMockHandler, settings);
    ((Factory) mock).setCallback(0, newFilter);
}
