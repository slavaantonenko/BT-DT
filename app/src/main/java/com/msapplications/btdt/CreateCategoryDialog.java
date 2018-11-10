package com.msapplications.btdt;

import android.app.AlertDialog;
import android.view.View;

public class CreateCategoryDialog
{
    private AlertDialog dialogCreateCategory;
    private AlertDialog.Builder verifyDialogBuilder;
    private View verifyDialogView;

    public CreateCategoryDialog(AlertDialog.Builder verifyDialogBuilder, View verifyDialogView)
    {
        this.verifyDialogBuilder = verifyDialogBuilder;
        this.verifyDialogView = verifyDialogView;

        this.verifyDialogBuilder.setView(this.verifyDialogView);
        this.dialogCreateCategory = this.verifyDialogBuilder.create();
        dialogCreateCategory.show();
    }

    public void dismiss()
    {
        this.dialogCreateCategory.dismiss();
    }
}
