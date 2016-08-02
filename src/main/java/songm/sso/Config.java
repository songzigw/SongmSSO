/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */

package songm.sso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 属性管理器
 * 
 * @author  zhangsong
 * @since   0.1, 2016-8-2
 * @version 0.1
 * 
 */
public class Config {

    private static Logger logger = Logger.getLogger(Config.class);

    /** 属性文件全名 */
    private static final String PFILE = "config-songmsso.properties";

    /** 属性文件的文件对象 */
    private File file;

    /** 属性文件的最后修改日期 */
    private long lastModified = 0;

    /** 属性文件对应的属性对象 */
    private Properties props;

    /** 本类存在的唯一实例 */
    private static Config instance = new Config();

    private Config() {
        loadFile();
    }

    private void loadFile() {
        if (file == null) {
            file = new File(Thread.currentThread()
                    .getContextClassLoader()
                    .getResource(PFILE).getFile());
        }
        // 检查属性文件被修改，重新读取此文件
        long newTime = file.lastModified();
        if (newTime > lastModified) {
            lastModified = newTime;
            if (props != null) {
                props.clear();
            } else {
                props = new Properties();
            }
            try {
                props.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                logger.error("File not found", e);
            } catch (IOException e) {
                logger.error("IO error", e);
            }
        }
    }

    /**
     * 静态工厂方法
     * 
     * @return
     */
    public static Config getInstance() {
        return instance;
    }

    /**
     * 读取一个特定的属性项
     * 
     * @param key
     * @return
     */
    public final Object getItem(String key) {
        loadFile();
        return props.getProperty(key);
    }

    /**
     * 获取系统的版本号
     * 
     * @return
     */
    public String getVersion() {
        return (String) this.getItem("version");
    }

    public String getClientKey() {
        return (String) this.getItem("client.key");
    }

    public String getClientSecret() {
        return (String) this.getItem("client.secret");
    }

    public int getServerPort() {
        String p = (String) getItem("server.prot");
        return Integer.parseInt(p);
    }
    
    public String getServerName() {
        return (String) getItem("server.name");
    }
}
