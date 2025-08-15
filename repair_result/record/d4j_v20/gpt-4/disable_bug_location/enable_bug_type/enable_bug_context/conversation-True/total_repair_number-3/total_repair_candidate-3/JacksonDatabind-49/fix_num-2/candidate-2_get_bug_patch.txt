public Object generateId(Object forPojo) {
    if (id != null) {
        return id;
    }
    id = generator.generateId(forPojo);
    return id;
}
