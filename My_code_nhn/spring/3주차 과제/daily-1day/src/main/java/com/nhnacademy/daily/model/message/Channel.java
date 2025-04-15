package com.nhnacademy.daily.model.message;

public enum Channel {
    A("https://nhnacademy.dooray.com/services/3204376758577275363/4045901689874472590/W0RgKMoPTUO3RejIIJVQcg"),
    B("https://nhnacademy.dooray.com/services/3204376758577275363/4045905507212963259/WL5QAUhXSXC09zQjupasRA");

    String url;
    Channel(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
