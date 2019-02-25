package com.msapplications.btdt.objects;

public enum CategoryType
{
    NOTES(0),
    COLLECTION(1),
    CINEMA_SEATS(2),
    TRAVEL(3);

    private int code;

    CategoryType(int code) {
        this.code = code;
    }

    public static CategoryType getType(int code)
    {
        for (CategoryType type : values())
            if (type.code == code)
                return type;

        return null;
    }

    public int getCode() {
        return code;
    }
}

