package com.msapplications.btdt.objects.itemTypes;

import android.graphics.Bitmap;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ItemInCategory implements Serializable
{
    String title;
    String description;
    Bitmap picture;

}
