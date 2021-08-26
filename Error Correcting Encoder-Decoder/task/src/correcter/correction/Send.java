package correcter.correction;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class Send {

    public static void perform() {

        /* Getting data from encoded.txt */

        String source = "";
        StringBuilder sourceBuilder = new StringBuilder();

        try {

            FileInputStream fileInputStream = new FileInputStream("encoded.txt");

            byte[] buffer = new byte[fileInputStream.available()];

            fileInputStream.read(buffer, 0, buffer.length);

            for (byte b : buffer) {
                sourceBuilder.append((char) Byte.toUnsignedInt(b));
            }

            source = sourceBuilder.toString();

        } catch (IOException e) {
            System.out.println("ERROR: something bad happened during opening file encoded.txt");
        }

        StringBuilder hexView = new StringBuilder();

        for (char character : source.trim().toCharArray()) {

            String toAppend = Integer.toHexString(character).toUpperCase(Locale.ROOT);

            if (toAppend.length() < 2) {
                toAppend = "0" + toAppend;
            }

            hexView.append(toAppend).append(" ");
        }

        StringBuilder sendTextBin = new StringBuilder();

        for (char character : source.toCharArray()) {
            String bin = Integer.toBinaryString(character);

            while (bin.length() < 8) {
                bin = "0" + bin;
            }

            sendTextBin.append(bin).append(" ");
        }

        System.out.println("\nencoded.txt:");
        System.out.println("hex view: " + hexView);
        System.out.println("bin view: " + sendTextBin);


        /* Saving data with simulated error to received.txt */

        StringBuilder receivedBinView = new StringBuilder();
        StringBuilder receivedHexView = new StringBuilder();

        for (String str : sendTextBin.toString().trim().split(" ")) {
            int value = Integer.parseInt(str, 2);
            int result = value ^ 1 << 3;
            StringBuilder stringBinResult = new StringBuilder(Integer.toBinaryString(result));

            while (stringBinResult.length() < 8) {
                stringBinResult = new StringBuilder("0" + stringBinResult);
            }

            String stringHexResult = Integer.toHexString(result).toUpperCase(Locale.ROOT);

            if (stringHexResult.length() < 2) {
                stringHexResult = "0" + stringHexResult;
            }

            receivedBinView.append(stringBinResult).append(" ");
            receivedHexView.append(stringHexResult).append(" ");

        }

        System.out.println("\nreceived.txt:");
        System.out.println("bin view: " + receivedBinView);
        System.out.println("hex view: " + receivedHexView);

        try {

            FileOutputStream fileOutputStream = new FileOutputStream("received.txt");

            for (String str : receivedBinView.toString().trim().split(" ")) {
                fileOutputStream.write((byte) Integer.parseInt(str, 2));
            }


            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("ERROR: something bad happened during writing data to file received.txt");
        }

    }
}
