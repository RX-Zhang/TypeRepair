public List<Connection.KeyVal> formData() {
    ArrayList<Connection.KeyVal> data = new ArrayList<Connection.KeyVal>();

    // iterate the form control elements and accumulate their values
    for (Element el: elements) {
        if (!el.tag().isFormSubmittable()) continue; // contents are form listable, superset of submitable
        String name = el.attr("name");
        if (name.length() == 0) continue;
        String type = el.attr("type");

        if ("select".equals(el.tagName())) {
            Elements options = el.select("option[selected]");
            boolean set = false;
            for (Element option: options) {
                data.add(HttpConnection.KeyVal.create(name, option.val()));
                set = true;
            }
            if (!set) {
                Element option = el.select("option").first();
                if (option != null)
                    data.add(HttpConnection.KeyVal.create(name, option.val()));
            }
        } else if ("checkbox".equalsIgnoreCase(type) || "radio".equalsIgnoreCase(type)) {
            // add checkbox or radio if they have the checked attribute
            if (el.hasAttr("checked")) {
                final String val = el.val();
                // Use "on" as the value if not explicitly set
                data.add(HttpConnection.KeyVal.create(name, val.isEmpty() ? "on" : val));
            }
        } else {
            // For disabled fields, skip adding them to the data
            if (!el.hasAttr("disabled")) {
                data.add(HttpConnection.KeyVal.create(name, el.val()));
            }
        }
    }
    return data;
}
