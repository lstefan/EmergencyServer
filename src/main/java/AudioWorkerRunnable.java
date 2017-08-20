import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 */
public class AudioWorkerRunnable implements Runnable {
    public DatagramSocket serverUDP;
    static int sampleRate = 8000;
    public AudioInputStream audioInputStream;

    public static AudioFormat format = new AudioFormat(sampleRate, 16, 1, true, false);

    public AudioWorkerRunnable(DatagramSocket serverUDP) {
        this.serverUDP = serverUDP;
    }

    public void run() {
        byte[] receiveData = new byte[640]; //[320];

        DatagramPacket pack = new DatagramPacket(receiveData, receiveData.length);
        int len = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        while (true) {
            try {
                serverUDP.receive(pack);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            receiveData = pack.getData();
            len++;


            out.write(receiveData, 0, receiveData.length);
            System.out.println("Am primit " + out.size());
            if (len == 200) {
                try {
                    out.flush();
                    out.close();
                } catch (final IOException ex) {
                    ex.printStackTrace();
                }

                // load bytes into the audio input stream for playback
                byte audioBytes[] = out.toByteArray();
                ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);

                int frameSizeInBytes = format.getFrameSize();
                audioInputStream = new AudioInputStream(bais, format, audioBytes.length / frameSizeInBytes);

                try {
                    audioInputStream.reset();
                } catch (final Exception ex) {
                    ex.printStackTrace();
                    return;
                }

                TestSound.saveToFile(AudioFileFormat.Type.WAVE, audioInputStream);
                len = 0;

                out = new ByteArrayOutputStream();
            }
        }

    }
}
