package com.demo.model;

import java.util.Comparator;

public class VmSortByMemory implements Comparator<VmDetail> {

    @Override
    public int compare(VmDetail vm1, VmDetail vm2) {
        return (int)(vm1.getRam()-vm2.getRam());
    }
}

