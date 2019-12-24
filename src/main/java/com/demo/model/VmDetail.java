package com.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Table(name="vmdetail")
public class VmDetail {
    @Id
    @Column(name="vmid",nullable=false)
    private Long vmId;

    @Column(name="vm_name",nullable=false)
    private String vmName;

    @Column(name="os",nullable=false)
    private String os;

    @Column(name="ram",nullable=false)
    private Long ram;

    @Column(name="harddisk",nullable=false)
    private Long hardDisk;

    @Column(name="cpu_cores",nullable=false)
    private int cpuCores;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "vm_userid")
    private VmUser vmUser;

    public Long getVmId() {
        return vmId;
    }

    public void setVmId(Long vmId) {
        this.vmId = vmId;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Long getRam() {
        return ram;
    }

    public void setRam(Long ram) {
        this.ram = ram;
    }

    public Long getHardDisk() {
        return hardDisk;
    }

    public void setHardDisk(Long hardDisk) {
        this.hardDisk = hardDisk;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public VmUser getVmUser() {
        return vmUser;
    }

    public void setVmUser(VmUser vmUser) {
        this.vmUser = vmUser;
    }

    @Override
    public String toString() {
        return "VmDetail{" +
                "vmId=" + vmId +
                ", vmName='" + vmName + '\'' +
                ", os='" + os + '\'' +
                ", ram=" + ram +
                ", hardDisk=" + hardDisk +
                ", cpuCores=" + cpuCores +
                ", user=" + vmUser +
                '}';
    }
}



