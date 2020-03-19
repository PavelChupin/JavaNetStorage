package com.gmail.pavelchupin.net_storage.common.files;

import com.gmail.pavelchupin.net_storage.common.oper.Operations;

import java.io.Serializable;
import java.util.Arrays;

public class FileSerializable implements Serializable /*Externalizable*/ {
    // private static final long serialVersionUID = 6941749676251341077L;

    private String path;
    private long lenght;
    private int part;
    private int partCount;
    private byte[] arr;
    private Operations oper;

    public FileSerializable(String path, long lenght, int part, int partCount, byte[] arr, Operations oper) {
        this.path = path;
        this.lenght = lenght;
        this.part = part;
        this.partCount = partCount;
        this.arr = arr;
        this.oper = oper;
    }

    public Operations getOper() {
        return oper;
    }

    @Override
    public String toString() {
        return "FileSerializable{" +
                "path='" + path + '\'' +
                ", lenght=" + lenght +
                ", part=" + part +
                ", partCount=" + partCount +
                ", arr=" + Arrays.toString(arr) +
                ", oper=" + oper +
                '}';
    }

    public int getPartCount() {
        return partCount;
    }

    public String getPath() {
        return path;
    }

    public long getLenght() {
        return lenght;
    }

    public int getPart() {
        return part;
    }

    public byte[] getArr() {
        return arr;
    }
/*
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(part);
        out.writeLong(lenght);
        out.writeInt(part);
        out.writeInt(partCount);
        out.write(arr);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.path = (Path) in.readObject();
        this.lenght = in.readLong();
        this.part = in.readInt();
        this.partCount = in.readInt();
        this.arr = ((String) in.readObject()).getBytes();
    }*/
}
