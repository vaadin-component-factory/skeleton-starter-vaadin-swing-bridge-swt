package com.example.swtbridge.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.swingbridge.swt.SwtBridge;

@Route(value = "")
public class WarehouseView extends VerticalLayout {
    private static final String MAIN_CLASS = "com.example.warehouse.swt.WarehouseApp";

    public WarehouseView() {
        setSizeFull();
        SwtBridge app = new SwtBridge(MAIN_CLASS);
        add(app);
        expand(app);
    }

}
