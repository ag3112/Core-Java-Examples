package com.practice.examples;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Intel on 7/15/2015.
 */
public class FTP {

    private String server = "ftp.abcxyzp.esy.es";
    private String userName = "u512150247.ankit";
    private String password = "25540083a";

    private int bufferSize = 1024 * 5;
    /*Specify remote directory to null, if your file is present at parent directory.*/
    private String remoteFileDirectory = "/abc";
    /*File name pattern */
    private String fileNamePattern = ".*.zip";

    /*Path where file get save locally*/
    private String localPath = "C:\\Users\\Intel\\Downloads";

    private FTPClient ftpClient;

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }

    public static void main(String[] args) {

        FTP ftp = new FTP();
        try {
            ftp.getClient();
            List<String> fileList = ftp.retrieveFileList();
            if (fileList.isEmpty()) {
                throw new FileNotFoundException("File not present at remote directory");
            }


            String fileName = "";
            if (fileList.size() == 1) {
                fileName = fileList.get(0);
            } else {
                for (String name : fileList) {
                    System.out.println("[DEBUG] Matched files [ " + name + " ]");
                    if (StringUtils.contains(name, "Full")) {
                        fileName = name;
                    }
                }
                if (StringUtils.isEmpty(fileName)) {
                    System.out.println("[WARNING] Two or more change files are present at remote directory");
                    fileName = fileList.get(0);
                }
            }

            System.out.println("Required file [ " + fileName + " ]");

            /*Strict checking, though it can not be empty*/
            if (StringUtils.isEmpty(fileName)) {
                throw new FileNotFoundException("File not present at remote directory");
            } else {
                ftp.getFile(fileName);
            }

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
    private List<String> retrieveFileList() throws IOException {
        FTPClient client = getFtpClient();
        if (StringUtils.isNotEmpty(remoteFileDirectory)) {
            System.out.println("Setting remote directory to [ " + remoteFileDirectory + " ]");
            client.changeWorkingDirectory(remoteFileDirectory);
        }

        Pattern pattern = Pattern.compile(fileNamePattern);

        List<String> fileList = new ArrayList<String>();

        for (FTPFile file : client.listFiles()) {
            System.out.println("[DEBUG] Remote File [ " + file.getName() + " ]");
            if (pattern.matcher(file.getName()).find()) {
                fileList.add(file.getName());
            }
        }

        return fileList;
    }

    /**
     * Method to get the FTP client and login the same with the given username and password
     *
     * @throws IOException
     */
    private void getClient() throws IOException {
        FTPClient client = new FTPClient();
        client.connect(server);
        if (client.isConnected()) {
            System.out.println("Connection established with the [ " + server + " ]");
        } else {
            throw new IOException("Unable to connect with server [ " + server + " ]");
        }
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
