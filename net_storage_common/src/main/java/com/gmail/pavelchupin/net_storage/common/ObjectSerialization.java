package com.gmail.pavelchupin.net_storage.common;

import com.gmail.pavelchupin.net_storage.common.files.FileSerializable;
import com.gmail.pavelchupin.net_storage.common.oper.Operations;

import java.io.Serializable;
import java.util.Map;

public class ObjectSerialization implements Serializable {
    private Operations oper;
    private FileSerializable file;
    private Map<String, String> dir;

    public ObjectSerialization(Operations oper, Map<String, String> dir) {
        this.oper = oper;
        this.dir = dir;
    }

    public ObjectSerialization(Operations oper, FileSerializable file) {
        this.oper = oper;
        this.file = file;
    }

    public Operations getOper() {
        return oper;
    }

    public FileSerializable getFile() {
        return file;
    }

    public Map<String, String> getDir() {
        return dir;
    }
}
