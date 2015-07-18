package com.practice.examples;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Intel on 7/15/2015.
 * Using Apache commons FTPClient v3.3 API.
 */
public class FTP {

    // FTP Server
    private String server = "";
    // Username to login to server
    private String userName = "";
    //Password
    private String password = "";
    /*Specify remote directory to null, if your file is present at parent directory.*/
    private String remoteFileDirectory = "";
    /*File name pattern to filter out required file(s)*/
    private String fileNamePattern = "";
    /*Path where file get save locally*/
    private String localPath = "";
    // Buffer size for file retrieval
    private int bufferSize = 1024 * 5;

    private FTPClient ftpClient;

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    /**
     * Starting point for FTP !!
     *
     * @param args
     */
    public static void main(String[] args) {

        FTP ftp = new FTP();
        try {
            ftp.getClient();
            FTPFile[] fileList = ftp.retrieveFileList();
            if (fileList.length == 0) {
                throw new FileNotFoundException("File not present at remote directory");
            }


            String fileName = "";
            if (fileList.length == 1) {
                fileName = fileList[0].getName();
            } else {
                // Logic to get a single file, if there are multiple files.
                for (FTPFile file : fileList) {
                    System.out.println("[DEBUG] Matched files [ " + file.getName() + " ]");
                    if (StringUtils.contains(file.getName(), "Full")) {
                        fileName = file.getName();
                    }
                }
                if (StringUtils.isEmpty(fileName)) {
                    System.out.println("[WARNING] Two or more change files are present at remote directory");
                    fileName = fileList[0].getName();
                }
            }

            System.out.println("Required file [ " + fileName + " ]");

            /*Strict checking, though it can not be empty*/
            if (StringUtils.isEmpty(fileName)) {
                throw new FileNotFoundException("File not present at remote directory");
            } else {
                ftp.getFile(fileName);
            }
            // Logging out !!
            ftp.getFtpClient().logout();
        } catch (Exception exp) {
            System.out.println("Unable to retrieve file [ " + exp + "]");
        }
    }

    /**
     * Method to get the required file from remote directory
     *
     * @param fileName
     * @throws IOException
     */
    private void getFile(String fileName) throws IOException {
        FTPClient client = getFtpClient();
        System.out.println("Retrieving file with name [ " + fileName + " ] at path [ " + localPath + " ]");
        if (client.retrieveFile(fileName, new FileOutputStream(localPath + fileName))) {
            System.out.println("File retrieved !!");
        }
    }

    /**
     * Method to retrieve the list of required files.
     *
     * @return
     * @throws IOException
     */
    private FTPFile[] retrieveFileList() throws IOException {
        FTPClient client = getFtpClient();
        FTPFileFilter filter = new FTPFileFilter() {
            Pattern pattern = Pattern.compile(fileNamePattern);
            public boolean accept(FTPFile ftpFile) {
                return pattern.matcher(ftpFile.getName()).find();
            }
        };

        return client.listFiles(remoteFileDirectory, filter);
    }

    /**
     * Method to get the FTP client and login the same with the given username and password
     *
     * @throws IOException
     */
    private void getClient() throws IOException {
        FTPClient client = new FTPClient();
        client.connect(server);
        System.out.println("Connection established with the [ " + server + " ]");
        client.setBufferSize(bufferSize);
        client.setFileType(FTPClient.BINARY_FILE_TYPE);
        System.out.println("Login to [ " + server + " ] with [ " + userName + " ]");
        if (client.login(userName, password)) {
            System.out.println("Login successful !!");
        } else {
            throw new IllegalArgumentException("Login Failed, Invalid credentials !!");
        }
        setFtpClient(client);
    }

}
