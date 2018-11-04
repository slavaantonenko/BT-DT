package com.msapplications.btdt.objects.itemTypes;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionItem
{
    String tag; //think about it
    ArrayList<ItemInCategory> subCollection;
}
