public List<Connection.KeyVal> formData() {
    ArrayList<Connection.KeyVal> data = new ArrayList<>();

    // iterate the form control elements and accumulate their values
    for (Element el: elements) {
        if (!el.tag().isFormSubmittable()) continue; // contents are form listable, superset of submitable
        if (el.hasAttr("disabled")) continue; // skip disabled form inputs
        String name = el.attr("name");
        if (name.length() == 0) continue;
        String tagName = el.normalName();
        String type = el.attr("type");

        // skip inputs with type "button", "submit", "reset", or "file"
        if ("input".equals(tagName)) {
            // exclude buttons, submits, resets and file inputs from submission
            if ("button".equalsIgnoreCase(type) || "submit".equalsIgnoreCase(type) 
                || "reset".equalsIgnoreCase(type) || "file".equalsIgnoreCase(type)) {
                continue;
            }
        }

        if ("select".equals(tagName)) {
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
            // only add checkbox or radio if they have the checked attribute
            if (el.hasAttr("checked")) {
                final String val = el.val().length() >  0 ? el.val() : "on";
                data.add(HttpConnection.KeyVal.create(name, val));
            }
        } else {
            data.add(HttpConnection.KeyVal.create(name, el.val()));
        }
    }
    return data;
}
