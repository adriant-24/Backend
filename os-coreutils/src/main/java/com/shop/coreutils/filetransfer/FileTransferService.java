package com.shop.coreutils.filetransfer;

import java.io.File;
import java.io.IOException;

public interface FileTransferService {

    public void upload(File file, String folderPath);

    public byte[] download(String path) throws IOException;

    public void deleteFile(String filePath);

    public TransferType getType();
}
