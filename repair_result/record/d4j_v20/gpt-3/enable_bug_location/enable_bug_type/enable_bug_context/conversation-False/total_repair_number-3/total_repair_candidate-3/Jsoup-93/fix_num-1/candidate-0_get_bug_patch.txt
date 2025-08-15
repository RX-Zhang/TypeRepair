public List<Connection.KeyVal> formData() {
    ArrayList<Connection.KeyVal> data = new ArrayList<>();

    // iterate the form control elements and accumulate their values
    for (Element el: elements) {
        if (!el.tag().isFormSubmittable()) continue; // contents are form listable, superset of submitable
        if (el.hasAttr("disabled")) continue; // skip disabled form inputs
        String name = el.attr("name");
        if (name.length() == 0) continue;
        String type = el.attr("type");

        if ("select".equals(el.normalName())) {
            Elements options = el.select("option[selected]");
            boolean set = false;
            for (Element option: options) {
                data.add(HttpConnection.KeyVal.create(name, option.val()));
                set = true;
            }
            if (!set) {
                Elements allOptions = el.select("option");
                if (!allOptions.isEmpty()) {
                    data.add(HttpConnection.KeyVal.create(name, allOptions.first().val()));
                }
            }
        } else if ("checkbox".equalsIgnoreCase(type) || "radio".equalsIgnoreCase(type)) {
            // only add checkbox or radio if they have the checked attribute
            if (el.hasAttr("checked")) {
                final String val = el.val().length() > 0 ? el.val() : "on";
                data.add(HttpConnection.KeyVal.create(name, val));
            }
        } else if (!"button".equalsIgnoreCase(type)) { // Exclude button type inputs
            data.add(HttpConnection.KeyVal.create(name, el.val()));
        }
    }
    return data;
}
