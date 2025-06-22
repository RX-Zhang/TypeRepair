public Object generateId(Object forPojo) {
    if (id == null) {
        try {
            id = generator.generateId(forPojo);
        } catch (Exception e) {
            // Handle potential exception from generator
            throw new RuntimeException("Failed to generate ID for the given POJO", e);
        }
    }
    return id;
}
