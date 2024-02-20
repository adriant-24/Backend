package com.shop.coreutils.filetransfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;

@Component
public class FileTransferFactory {

    EnumMap<TransferType,FileTransferService> fileTransferServicesMap;

    @Autowired
    FileTransferFactory(List<FileTransferService> fileTransferServices){
        fileTransferServicesMap = new EnumMap<>(TransferType.class);
        fileTransferServices.forEach(fileTransfer->
                fileTransferServicesMap.put(fileTransfer.getType(), fileTransfer));
    }

    public FileTransferService getFileTransferService(TransferType transferType){
        return this.fileTransferServicesMap.get(transferType);
    }
}
