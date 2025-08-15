public Object generateId(Object forPojo) {
    try {
        if (id == null) {
            id = generator.generateId(forPojo);
        }
    } catch (Exception e) {
        // Handle or log the exception if id generation fails
        throw new RuntimeException("Failed to generate Object Id", e);
    }
    return id;
}
