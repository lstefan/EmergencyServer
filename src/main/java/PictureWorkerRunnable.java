import message.Message;

import java.io.*;

/**

 */
public class PictureWorkerRunnable implements Runnable {
    ObjectInputStream input;
    ObjectOutputStream output;
    Message data;
    boolean authenticated = false;

    public PictureWorkerRunnable(ObjectInputStream input, ObjectOutputStream output) {
        this.input = input;
        this.output = output;
        this.authenticated = authenticateUser();
    }

    public boolean authenticateUser() {
        return true;
    }

    public void run() {
        while (authenticated) {
            try {
                data = (Message) input.readObject();
                readData();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void readData() {
        long time = System.currentTimeMillis();
        if (data.getType() == Message.PICTURE_FILE) {
            byte[] pictureData = data.getPicture();

            if (pictureData.length != 0) {
                OutputStream fileOutputStream = null;
                String fileName = "src/main/resources/img/img" + time + ".jpg";
                try {
                    fileOutputStream = new BufferedOutputStream(new FileOutputStream(fileName));
                    fileOutputStream.write(pictureData);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("Request processed: " + time);
            }
        }
    }
}