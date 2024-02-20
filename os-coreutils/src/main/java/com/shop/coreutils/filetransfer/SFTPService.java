package com.shop.coreutils.filetransfer;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class SFTPService implements FileTransferService{
    @Override
    public void upload(File file, String folderPath) {

    }

    @Override
    public byte[] download(String path) {
        return null;
    }

    @Override
    public void deleteFile(String filePath){

    }

    @Override
    public TransferType getType() {
        return TransferType.SFTP;
    }
}
