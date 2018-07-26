package com.capitalone.dashboard.editors;

import com.capitalone.dashboard.model.AppScanType;
import codesecurity.api.editors.CaseInsensitiveCodeSecurityTypeEditor;

public class CaseInsensitiveAppScanTypeEditor extends CaseInsensitiveCodeSecurityTypeEditor<AppScanType> {
    protected AppScanType getText(String text) {
        return AppScanType.fromString(text);
    }
}
