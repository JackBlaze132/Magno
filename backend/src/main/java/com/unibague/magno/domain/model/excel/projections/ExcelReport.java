package com.unibague.magno.domain.model.excel.projections;

// This class is used to encapsulate the content of an Excel report and its associated metadata.
public class ExcelReport<T> {

    private final byte[] content;
    private final T metadata;

    public ExcelReport(byte[] content, T metadata) {
        this.content = content;
        this.metadata = metadata;
    }

    public byte[] getContent() {
        return content;
    }

    public T getMetadata() {
        return metadata;
    }
}
