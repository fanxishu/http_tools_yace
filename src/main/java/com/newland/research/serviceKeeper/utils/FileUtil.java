//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.newland.research.serviceKeeper.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static final Logger log = LoggerFactory.getLogger(FileUtil.class);
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public FileUtil() {
    }

    public static LocalDateTime getLocaDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    public static String getLocaDateToString(LocalDateTime time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String localTime = df.format(time);
        return localTime;
    }

    public static LocalDateTime getStringToLocalDateTime(String time) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime ldt = LocalDateTime.parse(time, df);
        return ldt;
    }

    public static Date getLocalDateTimeToDate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    public static <T> T readFile(T t, File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            reader.readLine();
            String line = null;

            while((line = reader.readLine()) != null) {
                String[] item = line.split(",");
                String last = item[item.length - 1];
                System.out.println(last);
            }

            return t;
        } catch (Exception var6) {
            var6.printStackTrace();
            return t;
        }
    }

    public static String getDatePath() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime localDateTime = LocalDateTime.now();
        return df.format(localDateTime);
    }

    public static String getMonthPath() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMM");
        LocalDateTime localDateTime = LocalDateTime.now();
        return df.format(localDateTime);
    }

    public static String getDateTimePath() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime localDateTime = LocalDateTime.now();
        return df.format(localDateTime);
    }

    public static <T> List<List<T>> fixedGrouping(List<T> source, int n) {
        if (null != source && source.size() != 0 && n > 0) {
            List<List<T>> result = new ArrayList();
            int sourceSize = source.size();
            int size = source.size() / n + 1;

            for(int i = 0; i < size; ++i) {
                List<T> subset = new ArrayList();

                for(int j = i * n; j < (i + 1) * n; ++j) {
                    if (j < sourceSize) {
                        subset.add(source.get(j));
                    }
                }

                result.add(subset);
            }

            return result;
        } else {
            return null;
        }
    }

    public static void fileDelete(String dirPath) {
        Path path2 = Paths.get(dirPath);

        try {
            Files.delete(path2);
        } catch (IOException var3) {
            logger.error("fileDelete异常---" + var3);
        }

    }

    public static void createDir(String dir) throws IOException {
        Set<PosixFilePermission> perms = EnumSet.of(PosixFilePermission.OWNER_READ, PosixFilePermission.OWNER_WRITE, PosixFilePermission.OWNER_EXECUTE, PosixFilePermission.GROUP_READ);
        Path path = Paths.get(dir);

        try {
            Files.createDirectories(path, PosixFilePermissions.asFileAttribute(perms));
        } catch (Exception var6) {
            try {
                Files.createDirectories(path);
            } catch (Exception var5) {
                log.error("创建目录:" + dir + "失败!");
                throw new IOException("解析工作目录失败", var5);
            }
        }

    }

    public static void deleteDirs(String dirPath) {
        File file = new File(dirPath);
        if (file.isFile()) {
            fileDelete(dirPath);
        } else {
            File[] files = file.listFiles();
            if (files == null) {
                fileDelete(dirPath);
            } else {
                for(int i = 0; i < files.length; ++i) {
                    deleteDir(files[i].getAbsolutePath());
                }

                fileDelete(dirPath);
            }
        }

    }

    public static boolean deleteDir(String path) {
        File file = new File(path);
        return deleteFile(file);
    }

    public static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        } else if (file.isFile()) {
            file.delete();
            return true;
        } else {
            File[] files = file.listFiles();

            for(int i = 0; i < files.length; ++i) {
                deleteFile(files[i]);
            }

            file.delete();
            return true;
        }
    }

    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
