package com.msapplications.btdt.objects.itemTypes;

import android.graphics.Bitmap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ItemInCategory
{
    String title;
    String description;
    Bitmap picture;

}
