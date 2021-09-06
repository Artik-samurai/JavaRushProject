package BigTask_Archiver;

public class FileProperties {
    private String name;
    private long size;
    private long compressedSize;
    private int compressionMethod;

    public FileProperties(String name, long size, long compressedSize, int compressionMethod) {
        this.name = name;
        this.size = size;
        this.compressedSize = compressedSize;
        this.compressionMethod = compressionMethod;
    }

    @Override
    public String toString() {
        if (size > 0){
            long CompressionRatio = getCompressionRatio();
            return String.format("%s\t%d Kb (%d Kb) сжатие: %d%%", name, size/1024 , compressedSize/1024, CompressionRatio);
        } else return name;
    }

    public long getCompressionRatio(){
        return 100 - ((compressedSize * 100) / size);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setCompressedSize(long compressedSize) {
        this.compressedSize = compressedSize;
    }

    public void setCompressionMethod(int compressionMethod) {
        this.compressionMethod = compressionMethod;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public long getCompressedSize() {
        return compressedSize;
    }

    public int getCompressionMethod() {
        return compressionMethod;
    }
}
