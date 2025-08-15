public Object generateId(Object forPojo) {
    if (id == null) {
        try {
            id = generator.generateId(forPojo);
        } catch (Exception e) {
            // Log the exception or rethrow as needed
            throw new RuntimeException("Failed to generate ObjectId", e);
        }
    }
    return id;
}
