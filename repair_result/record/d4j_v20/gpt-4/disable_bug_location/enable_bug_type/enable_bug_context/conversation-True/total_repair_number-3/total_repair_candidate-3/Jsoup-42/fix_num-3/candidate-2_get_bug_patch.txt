public List<Connection.KeyVal> formData() {
    ArrayList<Connection.KeyVal> data = new ArrayList<Connection.KeyVal>();

    for (Element el : elements()) {
        if (!el.tag().isFormSubmittable()) continue;
        String name = el.attr("name");
        if (name.length() == 0) continue;
        String tagName = el.tagName();
        String type = el.attr("type");

        if ("select".equals(tagName)) {
            Elements options = el.select("option[selected]");
            boolean set = false;
            for (Element option : options) {
                data.add(HttpConnection.KeyVal.create(name, option.val()));
                set = true;
            }
            if (!set) {
                Element option = el.select("option").first();
                if (option != null)
                    data.add(HttpConnection.KeyVal.create(name, option.val()));
            }
        } else if ("checkbox".equalsIgnoreCase(type)) {
            if (el.hasAttr("checked")) {
                String val = el.hasAttr("value") && el.attr("value").length() > 0 ? el.attr("value") : "on";
                data.add(HttpConnection.KeyVal.create(name, val));
            }
        } else if ("radio".equalsIgnoreCase(type)) {
            if (el.hasAttr("checked")) {
                String val = el.val();
                data.add(HttpConnection.KeyVal.create(name, val));
            }
        } else {
            if (!el.hasAttr("disabled")) {
                data.add(HttpConnection.KeyVal.create(name, el.val()));
            }
        }
    }
    return data;
}
