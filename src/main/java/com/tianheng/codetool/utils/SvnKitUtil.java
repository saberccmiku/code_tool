package com.tianheng.codetool.utils;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Component;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.io.File;
import java.util.List;

@Component
public class SvnKitUtil {

    private SVNClientManager clientManager;
    private ISVNAuthenticationManager authManager;
    private SVNRepository repository;


    private void init() {
        try {
            // 身份验证
            authManager = SVNWCUtil.createDefaultAuthenticationManager(PropertiesUtil.getProperty("codetool.svn.user"), PropertiesUtil.getProperty("codetool.svn.pwd"));
        } catch (Exception e) {
            throw new RuntimeException("SVN身份认证失败：" + e.getMessage());
        }
        // 初始化版本库
        setupLibrary();
        try {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(PropertiesUtil.getProperty("codetool.svn.url")));
        } catch (SVNException e) {
            throw new RuntimeException("SVN创建库连接失败：" + e.getMessage());
        }

        // 创建身份验证管理器
        repository.setAuthenticationManager(authManager);
        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);
        try {
            //创建SVN实例管理器
            clientManager = SVNClientManager.newInstance(options, authManager);
        } catch (Exception e) {
            throw new RuntimeException("SVN实例管理器创建失败：" + e.getMessage());
        }

    }

    /**
     * 通过不同的协议初始化版本库
     */
    private void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    public void commit(JSONArray fileArr) throws Exception {
        for (int i = 0; i < fileArr.size(); i++) {
            //要提交的文件
            File commitFile = new File(fileArr.getJSONObject(i).getString("filePath"));
            //获取此文件的状态（是文件做了修改还是新添加的文件？）
            //把此文件增加到版本库中
            this.clientManager.getWCClient().doAdd(commitFile, false, false, false, SVNDepth.INFINITY, false, false);
            //提交此文件
            this.clientManager.getCommitClient().doCommit(
                    new File[]{commitFile}, true, "", null, null, true, false, SVNDepth.INFINITY);
        }
    }

    /**
     * 确定path是否是一个工作空间
     *
     * @param path 文件路径
     * @return 返回信息
     * @throws SVNException 异常信息
     */
    private boolean isWorkingCopy(File path) throws SVNException {
        if (!path.exists()) {
            return false;
        }
        try {
            if (null == SVNWCUtil.getWorkingCopyRoot(path, false)) {
                return false;
            }
        } catch (SVNException e) {
            throw new RuntimeException("确定path是否是一个工作空间 失败：" + e.getMessage());
        }
        return true;
    }

    /**
     * 递归检查不在版本控制的文件，并add到svn
     *
     * @param wc 检查的文件
     * @throws SVNException 异常信息
     */
    public void checkVersiondDirectory(File wc, List<File> files) throws SVNException {
        if (this.clientManager == null) {
            this.init();
        }
        if (!SVNWCUtil.isVersionedDirectory(wc)) {
            files.add(wc);
        }
        if (wc.isDirectory()) {
            for (File sub : wc.listFiles()) {
                if (sub.isDirectory() && sub.getName().equals(".svn")) {
                    continue;
                }
                checkVersiondDirectory(sub, files);
            }
        }
    }

    public void findCommitFiles(File dir, List<File> files) throws SVNException {
        if (this.clientManager == null) {
            this.init();
        }
        if (dir.isDirectory()) {
            for (File sub : dir.listFiles()) {
                if (sub.isDirectory() && sub.getName().equals(".svn")) {
                    continue;
                }
                findCommitFiles(sub, files);
            }
        } else {
            boolean checked = checkInSvn(dir);
            if (!checked) {
                files.add(dir);
            }
        }
    }

    private boolean checkInSvn(File file) {

        //获取此文件的状态（是文件做了修改还是新添加的文件？）
        SVNStatus status = null;
        try {
            status = clientManager.getStatusClient().doStatus(file, true);
        } catch (SVNException e) {
            e.printStackTrace();
        }
        //如果此文件是新增加的则先把此文件添加到版本库，然后提交。
        if (status == null) {
            return false;
        } else {
            return true;
        }
    }

}
