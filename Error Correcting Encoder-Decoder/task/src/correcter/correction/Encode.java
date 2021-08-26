package correcter.correction;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class Encode {

    public static void perform() {

        /* Getting data from file send.txt */


        // Getting source text
        String sendText = "";

        try {

            BufferedReader fileReader = new BufferedReader(new FileReader("send.txt"));
            sendText = fileReader.readLine();
            fileReader.close();

        } catch (IOException e) {
            System.out.println("ERROR: something bad happened during opening file send.txt");
        }


        // Getting source text in HEX
        StringBuilder sendTextHex = new StringBuilder();

        for (char character : sendText.toCharArray()) {
            sendTextHex.append(Integer.toHexString(character).toUpperCase(Locale.ROOT)).append(" ");
        }


        // Getting source text in binary
        StringBuilder sendTextBin = new StringBuilder();

        for (char character : sendText.toCharArray()) {
            String bin = Integer.toBinaryString(character);

            while (bin.length() < 8) {
                bin = "0" + bin;
            }

            sendTextBin.append(bin).append(" ");
        }


        System.out.println("\nsend.txt:");
        System.out.println("text view: " + sendText);
        System.out.println("hex view: " + sendTextHex);
        System.out.println("bin view: " + sendTextBin);


        /* Processing and writing data to encoded.txt */


        // Getting expand and encoded parity strings

        String sendTextBinNoSpaces = sendTextBin.toString().replaceAll(" ", "");
        StringBuilder encodedExpandBin = new StringBuilder();

        int counter = 0;

        for (int i = 3; i < sendTextBinNoSpaces.length(); i += 4) {

            encodedExpandBin
                    .append("..")
                    .append(sendTextBinNoSpaces.charAt(i - 3))
                    .append(".")
                    .append(sendTextBinNoSpaces.charAt(i - 2))
                    .append(sendTextBinNoSpaces.charAt(i - 1))
                    .append(sendTextBinNoSpaces.charAt(i))
                    .append(". ");
        }

        StringBuilder parityBuilder = new StringBuilder();

        for (String str : encodedExpandBin.toString().trim().split(" ")) {

            String firstBit = "";
            String secondBit = "";
            String thirdBit = String.valueOf(str.charAt(2));
            String fourthBit = "";
            String fifthBit = String.valueOf(str.charAt(4));
            String sixthBit = String.valueOf(str.charAt(5));
            String seventhBit = String.valueOf(str.charAt(6));
            String eighthBit = "0";

            if ((Integer.parseInt(thirdBit) + Integer.parseInt(fifthBit) + Integer.parseInt(seventhBit)) % 2 == 0) {
                firstBit = "0";
            } else {
                firstBit = "1";
            }

            if ((Integer.parseInt(thirdBit) + Integer.parseInt(sixthBit) + Integer.parseInt(seventhBit)) % 2 == 0) {
                secondBit = "0";
            } else {
                secondBit = "1";
            }

            if ((Integer.parseInt(fifthBit) + Integer.parseInt(sixthBit) + Integer.parseInt(seventhBit)) % 2 == 0) {
                fourthBit = "0";
            } else {
                fourthBit = "1";
            }

            parityBuilder
                    .append(firstBit)
                    .append(secondBit)
                    .append(thirdBit)
                    .append(fourthBit)
                    .append(fifthBit)
                    .append(sixthBit)
                    .append(seventhBit)
                    .append(eighthBit)
                    .append(" ");

        }

        StringBuilder parityHexText = new StringBuilder();

        for (String str : parityBuilder.toString().trim().split(" ")) {
            String toAppend = Integer.toHexString(Integer.parseInt(str, 2)).toUpperCase(Locale.ROOT);

            if (toAppend.length() < 2) {
                toAppend = "0" + toAppend;
            }

            parityHexText.append(toAppend).append(" ");
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream("encoded.txt");

            for (String str : parityBuilder.toString().trim().split(" ")) {
                fileOutputStream.write(Integer.parseInt(str, 2));
            }

            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("ERROR: something bad happened during writing data to file encoded.txt");
        }

        System.out.println("\nencoded.txt:");
        System.out.println("expand: " + encodedExpandBin);
        System.out.println("parity: " + parityBuilder);
        System.out.println("hex view: " + parityHexText);

    }
}
