package net.jackbauer.study;

import java.io.File;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class DirectoryFile {

    public static void main(String[] args) {

        String isDir = "D:/";
                
        // 하위 디렉토리 
        for (File info : new File(isDir).listFiles()) {
            if (info.isDirectory()) {
                System.out.println(info.getName());
            }
            if (info.isFile()) {
                System.out.println(info.getName());
            }
        }
        
        // 디렉토리 전체 용량
        System.out.println("전체 용량 : " +  FileUtils.sizeOfDirectory(new File(isDir)) + "Byte");
        
        
        // 하위의 모든 파일
        for (File info : FileUtils.listFiles(new File(isDir), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            System.out.println(info.getName());
        }
        
        // 하위의 모든 디렉토리
        for (File info : FileUtils.listFilesAndDirs(new File(isDir), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            if(info.isDirectory()) {
                System.out.println(info.getName());
            }
        }
        
    }

}