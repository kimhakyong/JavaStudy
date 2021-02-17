package net.jackbauer.study;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

public class ImageSizeDiff {
    public static void main(String[] args) {
    	String oldPath = "99.구버전";
    	String newPath = "00.최신버전";
       
        // 하위의 모든 파일
        for (File oldFile : FileUtils.listFiles(new File(oldPath), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
    		try {
    			File newFile = new File(oldFile.getAbsolutePath().replaceFirst(newPath, oldPath));

    			if (!newFile.exists()) {
    				System.out.println(oldFile.getAbsolutePath() + " 없음");
    			}
    			
    			BufferedImage oldBI = ImageIO.read(oldFile);
    			BufferedImage newBI = ImageIO.read(newFile);
    		
    			if (oldBI.getWidth() != newBI.getWidth() || oldBI.getHeight() != newBI.getWidth()) {
    				System.out.println(oldFile.getAbsolutePath() + " 사이즈오류");
    			}
    		} catch (Exception e) {
    			System.out.println(oldFile.getAbsolutePath() + " Exception");
    		}
        }
    }
}