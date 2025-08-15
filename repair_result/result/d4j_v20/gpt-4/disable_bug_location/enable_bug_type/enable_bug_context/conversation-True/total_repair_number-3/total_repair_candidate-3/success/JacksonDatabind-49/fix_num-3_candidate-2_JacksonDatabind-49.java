public Object generateId(Object forPojo) {
    if (id == null) {
        try {
            id = generator.generateId(forPojo);
        } catch (Exception e) {
            // Handle possible exceptions from ID generation gracefully
            throw new RuntimeException("Failed to generate Object Id", e);
        }
    }
    return id;
}
