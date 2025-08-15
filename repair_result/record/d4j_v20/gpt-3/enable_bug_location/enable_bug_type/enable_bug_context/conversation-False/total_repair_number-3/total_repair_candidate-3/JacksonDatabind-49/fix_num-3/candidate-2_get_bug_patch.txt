public Object generateId(Object forPojo) {
    // 04-Jun-2016, tatu: As per [databind#1255], need to consider possibility of
    //    id being generated for "alwaysAsId", but not being written as POJO; regardless,
    //    need to use existing id if there is one:
    try {
        if (forPojo == null) {
            throw new IllegalArgumentException("forPojo cannot be null");
        }
        id = generator.generateId(forPojo);
    } catch (Exception e) {
        // Handle potential exceptions here, e.g., log the error or rethrow it
        System.err.println("Error generating ID: " + e.getMessage());
        throw new RuntimeException("Failed to generate ID", e);
    }
    return id;
}
