package com.lite.jop.foundation.util;

import com.lite.jop.foundation.ServiceException;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ClassUtil
 *
 * @author LaineyC
 */
public class ClassUtil {

    /**
     * 从包package中获取所有的Class
     * @param packageName 包名
     * @return
     */
    public static List<Class<?>> findClass(String packageName){
        List<Class<?>> classes = new ArrayList<>();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    classes.addAll( findClass(packageName, filePath, recursive) );
                }
                else if ("jar".equals(protocol)){
                    JarFile jar;
                    try {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/') {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1) {
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                if ((idx != -1) || recursive){
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        try {
                                            classes.add(Class.forName(packageName + '.' + className));
                                        }
                                        catch (ClassNotFoundException e) {
                                            throw new ServiceException(e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException e) {
                        throw new ServiceException(e);
                    }
                }
            }
        }
        catch (IOException e) {
            throw new ServiceException(e);
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName 包名
     * @param packagePath 包物理路径
     * @param recursive 是否递归
     */
    public static List<Class<?>> findClass(String packageName, String packagePath, final boolean recursive){
        List<Class<?>> classes = new ArrayList<>();
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return classes;
        }
        File[] dirFiles = dir.listFiles( file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));
        for (File file : dirFiles) {
            if (file.isDirectory()) {
                classes.addAll( findClass(packageName + "." + file.getName(), file.getAbsolutePath(), recursive) );
            }
            else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    classes.add( Class.forName(packageName + '.' + className) );
                }
                catch (ClassNotFoundException e) {
                    throw new ServiceException(e);
                }
            }
        }
        return classes;
    }
}
