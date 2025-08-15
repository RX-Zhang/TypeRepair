public Object generateId(Object forPojo) {
    if (id != null) {
        return id;
    }
    try {
        id = generator.generateId(forPojo);
    } catch (Exception e) {
        // Log or handle generator failure if needed
        throw new RuntimeException("Failed to generate id for POJO", e);
    }
    return id;
}
